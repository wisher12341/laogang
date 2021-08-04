package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.GetGroupCountRequest;
import com.lejian.laogang.controller.contract.request.GetOldmanRequest;
import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.controller.contract.response.GetLocationResponse;
import com.lejian.laogang.controller.contract.response.GetOldmanResponse;
import com.lejian.laogang.controller.contract.response.MapResponse;
import com.lejian.laogang.service.OldmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/oldman")
public class OldmanController {

    @Autowired
    private OldmanService service;

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
    @RequestMapping("/getAllLocation")
    public GetLocationResponse getAllLocation(@RequestBody OldmanParam request){
        GetLocationResponse response = new GetLocationResponse();
        response.setVoList(service.getAllLocation(request));
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
}
