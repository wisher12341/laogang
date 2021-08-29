package com.lejian.laogang.controller;


import com.google.common.collect.Lists;
import com.lejian.laogang.check.bo.CheckResultBo;
import com.lejian.laogang.controller.contract.request.*;
import com.lejian.laogang.controller.contract.response.GetLocationResponse;
import com.lejian.laogang.controller.contract.response.GetOldmanResponse;
import com.lejian.laogang.controller.contract.response.MapResponse;
import com.lejian.laogang.handler.ExcelHandler;
import com.lejian.laogang.service.OldmanService;
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

    /**
     * 获取老人 某数据的 值的数量分布
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getGroupCount")
    public MapResponse getGroupCount(@RequestBody GetGroupCountRequest request){
        MapResponse response = new MapResponse();
        response.setMap(service.getGroupCount(request.getFieldNameList(),request.getLabelIdList()));
        return response;
    }

    /**
     * 获取老人 基本信息的数量分布
     * where条件是oldman_attr
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getOldmanBaseGroupByAttr")
    public MapResponse getOldmanBaseGroupByAttr(@RequestBody GetGroupCountRequest request){
        MapResponse response = new MapResponse();
        response.setMap(service.getOldmanBaseGroupByAttr(request.getFieldNameList(),request.getTypeList()));
        return response;
    }


    /**
     * 获取老人年龄数量分布，带男女
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAgeGroupCount")
    public MapResponse getAgeGroupCount(@RequestBody GetGroupCountRequest request){
        MapResponse response = new MapResponse();
        response.setMap(service.getAgeGroupCount(request.getLabelIdList()));
        return response;
    }

    @ResponseBody
    @RequestMapping("/getByPage")
    public GetOldmanResponse getByPage(@RequestBody GetOldmanRequest request){
        GetOldmanResponse response = new GetOldmanResponse();
        response.setOldmanVoList(service.getByPage(request.getOldmanParam(),request.getPageParam()));
        return response;
    }


    /**
     * 获取老人全部坐标
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLocation")
    public GetLocationResponse getLocation(@RequestBody OldmanParam request){
        GetLocationResponse response = new GetLocationResponse();
        response.setVoList(service.getLocation(request));
        return response;
    }


    /**
     * 获取数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCount")
    public MapResponse getCount(@RequestBody List<OldmanParam> request){
        MapResponse response = new MapResponse();
        response.setMap(service.getCount(request));
        return response;
    }

    /**
     * 获取重点老人完成度
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getZdFinish")
    public MapResponse getZdFinish(@RequestBody GetZdFinishRequest request){
        MapResponse response = new MapResponse();
        response.setMap(service.getZdFinish(request.getGroup(),request.getOldmanParam()));
        return response;
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
            checkResultBoList=service.addOldmanByExcel(excelData);
        }
        ModelAndView mv=new ModelAndView("/oldman");
        mv.addObject("check",checkResultBoList);
        return mv;
    }


    /**
     * 获取老人多属性表 type的数量
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/attr/getTypeCount")
    public MapResponse getTypeCount(@RequestBody GetTypeCountRequest request){
        MapResponse response = new MapResponse();
        response.setMap(service.getTypeCount(request.getTypeList()));
        return response;
    }


    /**
     * 获取 多属性表 type,value下  ext 的分组统计
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/attr/getExtGroup")
    public MapResponse getExtGroup(@RequestBody GetExtGroupRequest request){
        MapResponse response = new MapResponse();
        response.setMap(service.getExtGroup(request.getType(),request.getValue()));
        return response;
    }

    /**
     * 获取 趋势数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTrend")
    public MapResponse getTrend(@RequestBody GetTrendRequest request){
        MapResponse response = new MapResponse();
        response.setMap(service.getTrend(request.getType()));
        return response;
    }
}
