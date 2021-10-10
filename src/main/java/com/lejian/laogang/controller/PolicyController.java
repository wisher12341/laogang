package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.GetByIdRequest;
import com.lejian.laogang.controller.contract.request.GetOrganRequest;
import com.lejian.laogang.controller.contract.request.GetPolicyRequest;
import com.lejian.laogang.controller.contract.request.PolicyAddRequest;
import com.lejian.laogang.controller.contract.response.GetOldmanResponse;
import com.lejian.laogang.controller.contract.response.GetOrganResponse;
import com.lejian.laogang.controller.contract.response.GetPolicyResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.service.OrganService;
import com.lejian.laogang.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 政策
 */
@Controller
@RequestMapping("/policy")
public class PolicyController {


    @Autowired
    private PolicyService service;

    @ResponseBody
    @RequestMapping("/add")
    public SuccessResponse add(@RequestBody PolicyAddRequest request){
        SuccessResponse response = new SuccessResponse();
        service.add(request.getPolicyParam(),request.getOldmanParam());
        return response;
    }


    @ResponseBody
    @RequestMapping("/getByPage")
    public GetPolicyResponse getByPage(@RequestBody GetPolicyRequest request){
        GetPolicyResponse response = new GetPolicyResponse();
        response.setVoList(service.getByPage(request.getPageParam(),request.getPolicyParam()));
        response.setCount(service.count(request.getPolicyParam()));
        return response;
    }


    @ResponseBody
    @RequestMapping("/getOldman")
    public GetOldmanResponse getOldman(@RequestBody GetByIdRequest request){
        GetOldmanResponse response = new GetOldmanResponse();
        response.setOldmanVoList(service.getOldmanByPage(request.getPageParam(),request.getId()));
        response.setCount(service.oldmanCount(request.getId()));
        return response;
    }
}
