package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class PolicyAddRequest {
    private PolicyParam policyParam;
    private OldmanParam oldmanParam;
}
