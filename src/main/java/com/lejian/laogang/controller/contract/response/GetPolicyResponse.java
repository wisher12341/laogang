package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.LabelVo;
import com.lejian.laogang.pojo.vo.PolicyVo;
import lombok.Data;

import java.util.List;

@Data
public class GetPolicyResponse {
    private List<PolicyVo> voList;
    private Long count;
}
