package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.GetLabelRequest;
import com.lejian.laogang.controller.contract.request.OldmanParam;
import com.lejian.laogang.controller.contract.response.GetLabelResponse;
import com.lejian.laogang.controller.contract.response.MapResponse;
import com.lejian.laogang.service.HomeService;
import com.lejian.laogang.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 居家养老
 */
@Controller
@RequestMapping("/home")
public class HomeController {


    @Autowired
    private HomeService service;

    @ResponseBody
    @RequestMapping("/getCount")
    public MapResponse getCount(@RequestBody OldmanParam oldmanParam){
        MapResponse response = new MapResponse();
        response.setMap(service.geCount(oldmanParam));
        return response;
    }


}
