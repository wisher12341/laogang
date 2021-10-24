package com.lejian.laogang.pojo.vo;

import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
public class IntelligentDeviceVo {
    private Integer id;
    private Integer oldmanId;
    private String  name;
    private String startTime;
    private String endTime;


}
