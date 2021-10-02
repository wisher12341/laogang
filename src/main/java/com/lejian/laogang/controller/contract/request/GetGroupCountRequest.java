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
    /**
     * attr表的 type属性
     */
    private List<Integer> typeList;
    private OldmanParam oldmanParam;
}
