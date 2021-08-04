package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class GetOldmanRequest {
    private PageParam pageParam;
    private OldmanParam oldmanParam;
}
