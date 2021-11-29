package com.lejian.laogang.controller;


import com.google.common.collect.Lists;
import com.lejian.laogang.controller.contract.request.AddLinkManParam;
import com.lejian.laogang.controller.contract.request.GetGpsRequest;
import com.lejian.laogang.controller.contract.request.GetLinkManRequest;
import com.lejian.laogang.controller.contract.request.IdListRequest;
import com.lejian.laogang.controller.contract.response.GetLinkManResponse;
import com.lejian.laogang.controller.contract.response.MapResponse;
import com.lejian.laogang.controller.contract.response.SuccessResponse;
import com.lejian.laogang.handler.BaiduMapHandler;
import com.lejian.laogang.service.LinkManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 政策
 */
@Controller
@RequestMapping("/map")
public class MapController {


    @ResponseBody
    @RequestMapping("/geocoding")
    public MapResponse geocoding(@RequestBody GetGpsRequest request){
        MapResponse response = new MapResponse();
        Pair<String,String> gps = BaiduMapHandler.geocoding(request.getAddress(),"上海市");
        if (gps !=null){
            response.getMap().put("lng",gps.getFirst());
            response.getMap().put("lat",gps.getSecond());
        }
        return response;
    }


}
