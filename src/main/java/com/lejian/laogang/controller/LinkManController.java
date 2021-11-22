package com.lejian.laogang.controller;


import com.google.common.collect.Lists;
import com.lejian.laogang.controller.contract.request.*;
import com.lejian.laogang.controller.contract.response.GetLinkManResponse;
import com.lejian.laogang.controller.contract.response.GetOldmanResponse;
import com.lejian.laogang.controller.contract.response.GetPolicyResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.service.LinkManService;
import com.lejian.laogang.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 政策
 */
@Controller
@RequestMapping("/linkman")
public class LinkManController {


    @Autowired
    private LinkManService service;

    @ResponseBody
    @RequestMapping("/add")
    public SuccessResponse add(@RequestBody AddLinkManParam request){
        SuccessResponse response = new SuccessResponse();
        service.add(request);
        return response;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public SuccessResponse add(@RequestBody IdListRequest request){
        SuccessResponse response = new SuccessResponse();
        service.delete(request.getIdList());
        return response;
    }


    @ResponseBody
    @RequestMapping("/getByPage")
    public GetLinkManResponse getByPage(@RequestBody GetLinkManRequest request){
        GetLinkManResponse response = new GetLinkManResponse();
        if (request.getParam().getOldmanId()==null){
            response.setCount(0L);
            response.setVoList(Lists.newArrayList());
            return response;
        }
        response.setVoList(service.getByPage(request.getPageParam(),request.getParam()));
        response.setCount(service.count(request.getParam()));
        return response;
    }

}
