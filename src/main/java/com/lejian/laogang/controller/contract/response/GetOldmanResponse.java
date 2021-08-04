package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.OldmanVo;
import lombok.Data;

import java.util.List;

@Data
public class GetOldmanResponse {
    private List<OldmanVo> oldmanVoList;
    /**
     * 老人总数量
     */
    private Long count;
}
