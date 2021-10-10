package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class GetPolicyRequest {
    private PageParam pageParam;
    private PolicyParam policyParam;
}
