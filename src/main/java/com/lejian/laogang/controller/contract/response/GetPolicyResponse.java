package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.LabelVo;
import lombok.Data;

import java.util.List;

@Data
public class GetLabelResponse {
    private List<LabelVo> voList;
}
