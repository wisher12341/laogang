package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.IntelligentDeviceVo;
import com.lejian.laogang.pojo.vo.LinkManVo;
import lombok.Data;

import java.util.List;

@Data
public class GetIDResponse {
    private List<IntelligentDeviceVo> voList;
    private Long count;
}
