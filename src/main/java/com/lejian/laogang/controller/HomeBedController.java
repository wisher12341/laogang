package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.GetHomeBedRequest;
import com.lejian.laogang.controller.contract.request.GetIDRequest;
import com.lejian.laogang.controller.contract.request.HomBedBParam;
import com.lejian.laogang.controller.contract.request.IntelligentDeviceParam;
import com.lejian.laogang.controller.contract.response.GetHomeBedResponse;
import com.lejian.laogang.controller.contract.response.GetIDResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.service.HomeBedService;
import com.lejian.laogang.service.IntelligentDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 政策
 */
@Controller
@RequestMapping("/homeBed")
public class HomeBedController {


    @Autowired
    private HomeBedService service;

    @ResponseBody
    @RequestMapping("/add")
    public SuccessResponse add(@RequestBody HomBedBParam request){
        SuccessResponse response = new SuccessResponse();
        service.add(request);
        return response;
    }


    @ResponseBody
    @RequestMapping("/getByPage")
    public GetHomeBedResponse getByPage(@RequestBody GetHomeBedRequest request){
        GetHomeBedResponse response = new GetHomeBedResponse();
        response.setVoList(service.getByPage(request.getPageParam(),request.getParam()));
        response.setCount(service.count(request.getParam()));
        return response;
    }

}
