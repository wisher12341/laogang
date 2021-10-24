package com.lejian.laogang.controller.contract.request;

import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class IntelligentDeviceParam {
    private Integer oldmanId;
    private String  name;
    private String startTime;
    private String endTime;


    public IntelligentDeviceEntity convertEntity() {
        IntelligentDeviceEntity entity = new IntelligentDeviceEntity();
        BeanUtils.copyProperties(this,entity);
        entity.setStartTime(DateUtils.stringToLocalDate(this.startTime,YY_MM_DD));
        entity.setEndTime(DateUtils.stringToLocalDate(this.endTime,YY_MM_DD));
        return entity;
    }

    public JpaSpecBo convert() {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        return jpaSpecBo;
    }
}
