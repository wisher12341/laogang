package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.pojo.vo.IntelligentDeviceVo;
import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class IntelligentDeviceBo extends BaseBo{
    private Integer id;
    private Integer oldmanId;
    private String  name;
    private LocalDate startTime;
    private LocalDate endTime;

    @Override
    public IntelligentDeviceEntity convert() {
        IntelligentDeviceEntity entity = new IntelligentDeviceEntity();
        BeanUtils.copyProperties(this,entity);
        return entity;
    }

    public static IntelligentDeviceBo convert(IntelligentDeviceEntity entity) {
        if(entity == null){
            return null;
        }
        IntelligentDeviceBo bo = new IntelligentDeviceBo();
        BeanUtils.copyProperties(entity,bo);
        return bo;
    }


    public IntelligentDeviceVo convertVo() {
        IntelligentDeviceVo vo = new IntelligentDeviceVo();
        BeanUtils.copyProperties(this,vo);
        vo.setStartTime(startTime.format(YY_MM_DD));
        vo.setEndTime(endTime.format(YY_MM_DD));
        return vo;
    }
}
