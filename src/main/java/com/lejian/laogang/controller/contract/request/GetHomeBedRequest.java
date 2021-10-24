package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class GetHomeBedRequest {
    private PageParam pageParam;
    private HomBedBParam param;
}
