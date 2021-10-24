package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.HomBedBVo;
import com.lejian.laogang.pojo.vo.IntelligentDeviceVo;
import lombok.Data;

import java.util.List;

@Data
public class GetHomeBedResponse {
    private List<HomBedBVo> voList;
    private Long count;
}
