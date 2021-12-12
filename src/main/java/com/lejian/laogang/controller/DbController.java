package com.lejian.laogang.controller;


import com.lejian.laogang.controller.contract.request.DbRequest;
import com.lejian.laogang.controller.contract.request.GetGpsRequest;
import com.lejian.laogang.controller.contract.request.IdListRequest;
import com.lejian.laogang.controller.contract.response.MapResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.handler.BaiduMapHandler;
import com.lejian.laogang.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据库童谣操作
 */
@Controller
@RequestMapping("/db")
public class DbController {

    @Autowired
    private DbService service;

    @ResponseBody
    @RequestMapping("/delete")
    public SuccessResponse delete(@RequestBody DbRequest request){
        SuccessResponse response = new SuccessResponse();
        service.delete(request.getIdList(),request.getTable());
        return response;
    }

}
