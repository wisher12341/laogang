package com.lejian.laogang.controller.contract.response;

import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.pojo.vo.OrganVo;
import lombok.Data;

import java.util.List;

@Data
public class GetOrganResponse {
    private List<OrganVo> organVoList;
    private Long count;
}
