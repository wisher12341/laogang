package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.LocationVo;
import lombok.Data;

import java.util.List;

@Data
public class GetLocationResponse {
    private List<LocationVo> voList;
}
