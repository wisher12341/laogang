package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.pojo.vo.HomBedBVo;
import com.lejian.laogang.pojo.vo.HomeDoctorVo;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class HomeDoctorBo extends BaseBo{
    private Integer id;
    private Integer oldmanId;

    private String  name;

    private String organ;

    private LocalDate time;

    @Override
    public HomeDoctorEntity convert() {
        HomeDoctorEntity entity = new HomeDoctorEntity();
        BeanUtils.copyProperties(this,entity);
        return entity;
    }

    public static HomeDoctorBo convert(HomeDoctorEntity entity) {
        HomeDoctorBo bo = new HomeDoctorBo();
        BeanUtils.copyProperties(entity,bo);
        return bo;
    }


    public HomeDoctorVo convertVo() {
        HomeDoctorVo vo = new HomeDoctorVo();
        BeanUtils.copyProperties(this,vo);
        vo.setTime(time.format(YY_MM_DD));
        return vo;
    }
}
