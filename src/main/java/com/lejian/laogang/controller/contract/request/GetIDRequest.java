package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class GetIDRequest {
    private PageParam pageParam;
    private IntelligentDeviceParam param;
}
