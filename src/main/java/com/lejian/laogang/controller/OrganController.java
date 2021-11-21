package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.*;
import com.lejian.laogang.controller.contract.response.GetOrganOldmanResponse;
import com.lejian.laogang.controller.contract.response.GetOrganResponse;
import com.lejian.laogang.controller.contract.response.MapResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.pojo.vo.OrganOldmanVo;
import com.lejian.laogang.pojo.vo.OrganVo;
import com.lejian.laogang.service.HomeService;
import com.lejian.laogang.service.OrganOldmanService;
import com.lejian.laogang.service.OrganService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 机构
 */
@Controller
@RequestMapping("/organ")
public class OrganController {


    @Autowired
    private OrganService service;
    @Autowired
    private OrganOldmanService organOldmanService;

    @ResponseBody
    @RequestMapping("/getByPage")
    public GetOrganResponse getByPage(@RequestBody GetOrganRequest request){
        GetOrganResponse response = new GetOrganResponse();
        response.setOrganVoList(service.getByPage(request.getOrganParam(),request.getPageParam()));
        return response;
    }

    @ResponseBody
    @RequestMapping("/getById")
    public OrganVo getById(@RequestBody GetByIdRequest request){
        return service.getBYId(request.getId());
    }

    @ResponseBody
    @RequestMapping("/getByUser")
    public OrganVo getByUser(){
        return service.getByUser();
    }

    @ResponseBody
    @RequestMapping("/edit")
    public SuccessResponse edit(@RequestBody OrganParam request){
        SuccessResponse response = new SuccessResponse();
        service.edit(request);
        return response;
    }


    @ResponseBody
    @RequestMapping("/getOldmanByPage")
    public GetOrganOldmanResponse getOldmanByPage(@RequestBody GetOrganOldmanRequest request){
        GetOrganOldmanResponse response = new GetOrganOldmanResponse();
        response.setVoList(organOldmanService.getOldmanByPage(request.getParam(),request.getPageParam()));
        if (CollectionUtils.isNotEmpty(response.getVoList())) {
            request.getParam().setOrganId(response.getVoList().get(0).getOrganId());
        }
        response.setCount(organOldmanService.getOldmanCount(request.getParam()));
        return response;
    }

    @ResponseBody
    @RequestMapping("/getOldmanById")
    public OrganOldmanVo getOldmanById(@RequestBody OrganOldmanParam request){
        return organOldmanService.getById(request.getId());
    }


    @ResponseBody
    @RequestMapping("/editOrAddOldman")
    public SuccessResponse editOrAddOldman(@RequestBody OrganOldmanParam request){
        SuccessResponse response = new SuccessResponse();
        organOldmanService.editOrAdd(request);
        return response;
    }
}
