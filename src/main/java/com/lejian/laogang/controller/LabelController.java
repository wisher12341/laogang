package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.GetLabelRequest;
import com.lejian.laogang.controller.contract.response.GetLabelResponse;
import com.lejian.laogang.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/label")
public class LabelController {


    @Autowired
    private LabelService service;

    @ResponseBody
    @RequestMapping("/getLabel")
    public GetLabelResponse getLabel(@RequestBody GetLabelRequest request){
        GetLabelResponse response = new GetLabelResponse();
        response.setVoList(service.getLabel(request));
        return response;
    }

    @ResponseBody
    @RequestMapping("/getLabelFirst")
    public GetLabelResponse getLabelFirst(){
        GetLabelResponse response = new GetLabelResponse();
        response.setVoList(service.getLabelFirst());
        return response;
    }

}
