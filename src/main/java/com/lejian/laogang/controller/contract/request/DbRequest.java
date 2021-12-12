package com.lejian.laogang.controller.contract.request;

import lombok.Data;

import java.util.List;

@Data
public class DbRequest {
    private String table;
    private List<Integer> idList;
}
