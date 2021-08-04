package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class PageParam {
    private Integer pageNo;
    private Integer pageSize;
    private Boolean sort;
    private Boolean needCount = true;
}
