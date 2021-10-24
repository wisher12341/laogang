package com.lejian.laogang.controller.contract.request;

import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class HomeDoctorParam {
    private Integer id;
    private Integer oldmanId;

    private String  name;

    private String organ;

    private String time;

    public HomeDoctorEntity convertEntity() {
        HomeDoctorEntity entity = new HomeDoctorEntity();
        BeanUtils.copyProperties(this,entity);
        entity.setTime(DateUtils.stringToLocalDate(this.time,YY_MM_DD));
        return entity;
    }

    public JpaSpecBo convert() {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        return jpaSpecBo;
    }
}
