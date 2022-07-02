package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.DbRequest;
import com.lejian.laogang.controller.contract.request.GetByIdRequest;
import com.lejian.laogang.controller.contract.response.OperationLogResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.service.DbService;
import com.lejian.laogang.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 操作日志查询
 */
@Controller
@RequestMapping("/operation/log")
public class OperationLogController {

    @Autowired
    private OperationLogService service;

    @ResponseBody
    @RequestMapping("/oldman")
    public OperationLogResponse oldman(@RequestBody GetByIdRequest request){
        return service.oldman(request.getId());
    }

}
