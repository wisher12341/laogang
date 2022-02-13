package com.lejian.laogang.controller;

import com.google.common.collect.Lists;
import com.lejian.laogang.check.bo.CheckResultBo;
import com.lejian.laogang.controller.contract.request.EditOldmanParam;
import com.lejian.laogang.controller.contract.request.GetByIdRequest;
import com.lejian.laogang.controller.contract.request.GetOldmanRequest;
import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.controller.contract.response.GetOldmanResponse;
import com.lejian.laogang.controller.contract.response.GetOrganOldmanResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.handler.ExcelHandler;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.OldmanAttrBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.pojo.bo.UserBo;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.repository.OldmanAttrRepository;
import com.lejian.laogang.repository.OldmanRepository;
import com.lejian.laogang.repository.OrganOldmanRepository;
import com.lejian.laogang.repository.entity.OldmanAttrEntity;
import com.lejian.laogang.repository.entity.OrganOldmanEntity;
import com.lejian.laogang.service.OldmanService;
import com.lejian.laogang.util.LaogangContext;
import com.lejian.laogang.util.LaogangUserContext;
import com.lejian.laogang.util.StringUtils;
import com.lejian.laogang.util.UserUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/oldman")
public class OldmanController {

    @Autowired
    private OldmanService service;

    @Autowired
    private ExcelHandler excelHandler;



    @ResponseBody
    @RequestMapping("/getByPage")
    public GetOldmanResponse getByPage(@RequestBody GetOldmanRequest request){
        GetOldmanResponse response = new GetOldmanResponse();
        if (request.getOldmanParam()!=null && request.getOldmanParam().getIsPolicy()!=null && request.getOldmanParam().getIsPolicy()){
            request.setOldmanParam(LaogangUserContext.get("policyOldmanParam"));
        }
        UserBo userBo = UserUtils.getUser();
        response.setOldmanVoList(service.getByPage(request.getOldmanParam(),request.getPageParam(),userBo));
        response.setCount(service.getOldmanCount(request.getOldmanParam(),userBo));
        return response;
    }


    @ResponseBody
    @RequestMapping("/getById")
    public OldmanVo getById(@RequestBody GetByIdRequest request){
        return service.getBYId(request.getId());
    }


    @ResponseBody
    @RequestMapping("/editOrAdd")
    public SuccessResponse editOrAdd(@RequestBody EditOldmanParam request){
        service.editOrAdd(request);
        return new SuccessResponse();
    }


    @Autowired
    OrganOldmanRepository repository;
    @Autowired
    OldmanAttrRepository oldmanAttrRepository;
    @Autowired
    OldmanRepository oldmanRepository;
    /**
     * excel导入
     * 没有的添加  有的更新
     * @param file
     * @return
     */
    //todo 限制  数量限制 一次1000？ 参数限制
    @RequestMapping(value = "/importExcel",method = RequestMethod.POST)
    public ModelAndView importExcel(@RequestParam MultipartFile file) {
//        JpaSpecBo jpaSpecBo = new JpaSpecBo();
//        jpaSpecBo.getEqualMap().put("organId","1");
//        List<OrganOldmanEntity> data =repository.findEntityWithSpec(jpaSpecBo);
//
//        List<String> idCardList = data.stream().map(item->item.getIdCard().trim()).distinct().collect(Collectors.toList());
//        Map<String,OldmanBo> map = oldmanRepository.getByIdCards(idCardList).stream().collect(Collectors.toMap(OldmanBo::getIdCard, Function.identity()));
//        data.stream().filter(item->map.containsKey(item.getIdCard().trim())).forEach(item->{
//            OldmanAttrEntity entity = new OldmanAttrEntity();
//            entity.setOldmanId(map.get(item.getIdCard().trim()).getId());
//            entity.setIdCard(item.getIdCard().trim());
//            entity.setType(13);
//            entity.setValue(1);
//            entity.setExt("1");
//
//            oldmanAttrRepository.save(entity);
//        });


        Pair<List<String>,List<List<String>>> excelData=excelHandler.parse(file,1);
        List<CheckResultBo> checkResultBoList= Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(excelData.getSecond())) {
            if (file.getOriginalFilename().contains("经济条件")){
                checkResultBoList=service.addIncome(excelData);
            }else if(file.getOriginalFilename().contains("健康状态")){
                checkResultBoList=service.addHealth(excelData);
            }else if(file.getOriginalFilename().contains("居家养老")){
                checkResultBoList=service.addJujia(excelData);
            }else if(file.getOriginalFilename().contains("家庭医生")){
                checkResultBoList=service.addHomeDoctor(excelData);
            }else{
                checkResultBoList=service.addOldmanByExcel(excelData);
            }
        }
        ModelAndView mv=new ModelAndView("/oldman");
        mv.addObject("check",checkResultBoList);
        return mv;
    }

    @RequestMapping(value = "/exportExcel",method = RequestMethod.POST)
    public void eportExcel(@RequestParam(value = "ageStart",required = false) String ageStart,
                           @RequestParam(value = "ageEnd",required = false) String ageEnd,
                           @RequestParam(value = "male",required = false) String male,
                           @RequestParam(value = "isZd",required = false) String isZd,
                           @RequestParam(value = "huji",required = false) String huji,
                           @RequestParam(value = "rh",required = false) String rh,
                           @RequestParam(value = "politics",required = false) List<String> politics,
                           @RequestParam(value = "jkzk",required = false) List<String> jkzk,
                           @RequestParam(value = "eyesight",required = false) List<String> eyesight,
                           @RequestParam(value = "psychosis",required = false) List<String> psychosis,
                           @RequestParam(value = "haveDoctor",required = false) Integer haveDoctor,
                           @RequestParam(value = "family",required = false) List<String> family,
                           @RequestParam(value = "familyType",required = false) List<String> familyType,
                           @RequestParam(value = "income",required = false) List<String> income,
                           @RequestParam(value = "serviceStatus",required = false) List<String> serviceStatus,
                           @RequestParam(value = "jujia",required = false) List<String> jujia,
                           @RequestParam(value = "areaVillage",required = false) List<String> areaVillage) {
        OldmanParam param = new OldmanParam();
        param.setMale(male);
        param.setIsZd(isZd);
        param.setHuiji(huji);
        param.setRh(rh);
        param.setPoliticsList(politics);
        param.setJkzkList(jkzk);
        param.setEyesightList(eyesight);
        param.setPsychosisList(psychosis);
        param.setHaveDoctor(haveDoctor);
        param.setFamilyList(family);
        param.setFamilyTypeList(familyType);
        param.setIncomeList(income);
        param.setServiceStatusList(serviceStatus);
        param.setJujiaList(jujia);
        param.setAreaVillageList(areaVillage);
        if (StringUtils.isNotBlank(ageStart) || StringUtils.isNotBlank(ageEnd)){
            param.setAge(ageStart+"-"+ageEnd);
        }
        service.exportOldman(param);
    }
}
