package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.GetHomeBedRequest;
import com.lejian.laogang.controller.contract.request.GetHomeDoctorRequest;
import com.lejian.laogang.controller.contract.request.HomBedBParam;
import com.lejian.laogang.controller.contract.request.HomeDoctorParam;
import com.lejian.laogang.controller.contract.response.GetHomeBedResponse;
import com.lejian.laogang.controller.contract.response.GetHomeDoctorResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.service.HomeBedService;
import com.lejian.laogang.service.HomeDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 政策
 */
@Controller
@RequestMapping("/homeDoctor")
public class HomeDoctorController {


    @Autowired
    private HomeDoctorService service;

    @ResponseBody
    @RequestMapping("/add")
    public SuccessResponse add(@RequestBody HomeDoctorParam request){
        SuccessResponse response = new SuccessResponse();
        service.add(request);
        return response;
    }


    @ResponseBody
    @RequestMapping("/getByPage")
    public GetHomeDoctorResponse getByPage(@RequestBody GetHomeDoctorRequest request){
        GetHomeDoctorResponse response = new GetHomeDoctorResponse();
        response.setVoList(service.getByPage(request.getPageParam(),request.getParam()));
        response.setCount(service.count(request.getParam()));
        return response;
    }

}
