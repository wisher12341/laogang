package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.response.MapResponse;
import com.lejian.laogang.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 机构
 */
@Controller
@RequestMapping("/organ")
public class OrganController {


    @Autowired
    private HomeService service;

    @ResponseBody
    @RequestMapping("/getOrgan")
    public MapResponse getCount(){
        MapResponse response = new MapResponse();
        response.setMap(service.geCount());
        return response;
    }


}
