package com.lejian.laogang.check.bo;

import lombok.Data;

import java.util.List;

@Data
public class CheckResultBo {
    /**
     * 编号
     */
    private Integer number;
    private List<CheckFieldBo> checkFieldBoList;
}
