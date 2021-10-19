package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.LinkManVo;
import com.lejian.laogang.pojo.vo.PolicyVo;
import lombok.Data;

import java.util.List;

@Data
public class GetLinkManResponse {
    private List<LinkManVo> voList;
    private Long count;
}
