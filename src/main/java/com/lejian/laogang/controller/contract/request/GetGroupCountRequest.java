package com.lejian.laogang.controller.contract.request;

import lombok.Data;

import java.util.List;

@Data
public class GetGroupCountRequest {
    /**
     * key field
     * value table
     */
    private List<String> fieldNameList;
    private List<String> labelIdList;
    private List<Integer> typeList;
}
