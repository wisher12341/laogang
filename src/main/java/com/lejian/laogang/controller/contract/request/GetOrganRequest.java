package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class GetOrganRequest {
    private PageParam pageParam;
    private OldmanParam oldmanParam;
}
