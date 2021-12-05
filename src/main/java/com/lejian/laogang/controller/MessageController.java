package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.GetByIdRequest;
import com.lejian.laogang.controller.contract.request.GetPolicyRequest;
import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.controller.contract.request.PolicyAddRequest;
import com.lejian.laogang.controller.contract.response.GetMessageResponse;
import com.lejian.laogang.controller.contract.response.GetOldmanResponse;
import com.lejian.laogang.controller.contract.response.GetPolicyResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.pojo.bo.UserBo;
import com.lejian.laogang.service.MessageService;
import com.lejian.laogang.service.PolicyService;
import com.lejian.laogang.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 消息
 */
@Controller
@RequestMapping("/message")
public class MessageController {


    @Autowired
    private MessageService service;



    @ResponseBody
    @RequestMapping("/getByPage")
    public GetMessageResponse getByPage(@RequestBody PageParam pageParam){
        GetMessageResponse response = new GetMessageResponse();
        UserBo userBo = UserUtils.getUser();
        response.setVoList(service.getByPage(pageParam,userBo));
        response.setCount(service.count(pageParam,userBo));
        return response;
    }

}
