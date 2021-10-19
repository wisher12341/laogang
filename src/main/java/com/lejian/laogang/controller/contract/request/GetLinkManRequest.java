package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class GetLinkManRequest {
    private PageParam pageParam;
    private LinkManParam param;
}
