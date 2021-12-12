package com.lejian.laogang.controller;

import com.google.common.collect.Lists;
import com.lejian.laogang.check.bo.CheckResultBo;
import com.lejian.laogang.controller.contract.request.EditOldmanParam;
import com.lejian.laogang.controller.contract.request.GetByIdRequest;
import com.lejian.laogang.controller.contract.request.GetOldmanRequest;
import com.lejian.laogang.controller.contract.response.GetOldmanResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.handler.ExcelHandler;
import com.lejian.laogang.pojo.bo.UserBo;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.service.OldmanService;
import com.lejian.laogang.util.UserUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    /**
     * excel导入
     * 没有的添加  有的更新
     * @param file
     * @return
     */
    //todo 限制  数量限制 一次1000？ 参数限制
    @RequestMapping(value = "/importExcel",method = RequestMethod.POST)
    public ModelAndView importExcel(@RequestParam MultipartFile file) {
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
}
