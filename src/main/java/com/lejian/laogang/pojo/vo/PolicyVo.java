package com.lejian.laogang.pojo.vo;

import com.lejian.laogang.controller.contract.request.PolicyParam;
import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.repository.entity.PolicyEntity;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class PolicyVo{
    private Integer id;

    private String name;

    private String content;
    private String type;

    private String wh;


    private String startTime;
    private String endTime;
    private String createTime;


}
