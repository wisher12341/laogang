package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;


public class IntelligentDeviceBo extends BaseBo{
    private Integer id;
    private Integer oldmanId;
    private String  name;
    private LocalDate startTime;
    private LocalDate endTime;

    @Override
    public <Entity> Entity convert() {
        return null;
    }

    public static IntelligentDeviceBo convert(IntelligentDeviceEntity entity) {
        return null;
    }

}
