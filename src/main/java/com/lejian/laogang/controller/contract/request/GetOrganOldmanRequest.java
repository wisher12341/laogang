package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class GetOrganOldmanRequest {
    private PageParam pageParam;
    private OrganOldmanParam param;
}
