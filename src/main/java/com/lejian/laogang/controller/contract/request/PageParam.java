package com.lejian.laogang.controller.contract.request;

import com.lejian.laogang.pojo.bo.JpaSpecBo;
import lombok.Data;

@Data
public class PageParam {
    private Integer pageNo;
    private Integer pageSize;
    private Boolean sort;
    private Boolean needCount = true;

}
