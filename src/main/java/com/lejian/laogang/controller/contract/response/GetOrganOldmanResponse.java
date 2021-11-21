package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.OrganOldmanVo;
import com.lejian.laogang.pojo.vo.OrganVo;
import lombok.Data;

import java.util.List;

@Data
public class GetOrganOldmanResponse {
    private List<OrganOldmanVo> voList;
    private Long count;
}
