package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.AddLinkManParam;
import com.lejian.laogang.controller.contract.request.GetIDRequest;
import com.lejian.laogang.controller.contract.request.GetLinkManRequest;
import com.lejian.laogang.controller.contract.request.IntelligentDeviceParam;
import com.lejian.laogang.controller.contract.response.GetIDResponse;
import com.lejian.laogang.controller.contract.response.GetLinkManResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.service.IntelligentDeviceService;
import com.lejian.laogang.service.LinkManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 政策
 */
@Controller
@RequestMapping("/intelligentDevice")
public class IntelligentDeviceController {


    @Autowired
    private IntelligentDeviceService service;

    @ResponseBody
    @RequestMapping("/add")
    public SuccessResponse add(@RequestBody IntelligentDeviceParam request){
        SuccessResponse response = new SuccessResponse();
        service.add(request);
        return response;
    }


    @ResponseBody
    @RequestMapping("/getByPage")
    public GetIDResponse getByPage(@RequestBody GetIDRequest request){
        GetIDResponse response = new GetIDResponse();
        response.setVoList(service.getByPage(request.getPageParam(),request.getParam()));
        response.setCount(service.count(request.getParam()));
        return response;
    }

}
