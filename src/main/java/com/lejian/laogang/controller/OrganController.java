package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.GetByIdRequest;
import com.lejian.laogang.controller.contract.request.GetOrganRequest;
import com.lejian.laogang.controller.contract.response.GetOrganResponse;
import com.lejian.laogang.controller.contract.response.MapResponse;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.pojo.vo.OrganVo;
import com.lejian.laogang.service.HomeService;
import com.lejian.laogang.service.OrganService;
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

}
