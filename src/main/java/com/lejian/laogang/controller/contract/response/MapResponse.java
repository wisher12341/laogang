package com.lejian.laogang.controller.contract.response;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class MapResponse {
    private Map<String,Object> map = Maps.newHashMap();
}
