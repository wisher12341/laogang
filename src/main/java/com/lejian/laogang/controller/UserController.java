package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.DbRequest;
import com.lejian.laogang.controller.contract.request.UserParam;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.service.DbService;
import com.lejian.laogang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 账户
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @ResponseBody
    @RequestMapping("/edit")
    public SuccessResponse delete(@RequestBody UserParam request){
        SuccessResponse response = new SuccessResponse();
        service.edit(request);
        return response;
    }

}
