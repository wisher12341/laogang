package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class GetByIdRequest {
    private Integer id;
    private PageParam pageParam;
}
