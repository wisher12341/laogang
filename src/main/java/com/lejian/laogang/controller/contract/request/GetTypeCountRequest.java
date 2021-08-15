package com.lejian.laogang.controller.contract.request;

import lombok.Data;

import java.util.List;

@Data
public class GetTypeCountRequest {
    private List<Integer> typeList;
}
