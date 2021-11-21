package com.lejian.laogang.pojo.vo;

import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.repository.entity.OrganOldmanEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
public class OrganOldmanVo {
    private Integer id;
    private String name;
    private String idCard;
    private String bodyDesc;
    private String startTime;
    private String endTime;
    private String bed;
    private Integer organId;
    private Integer status;
    private String body;
}
