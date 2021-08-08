package com.lejian.laogang.controller.contract.request;

import lombok.Data;

@Data
public class GetZdFinishRequest {
    private String group;
    private OldmanParam oldmanParam;
}
