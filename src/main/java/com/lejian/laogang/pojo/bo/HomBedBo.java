package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.pojo.vo.HomBedBVo;
import com.lejian.laogang.pojo.vo.IntelligentDeviceVo;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class HomBedBo extends BaseBo{

    private Integer id;

    private Integer oldmanId;

    private String organ;

    private LocalDate time;


    @Override
    public HomeBedEntity convert() {
        HomeBedEntity entity = new HomeBedEntity();
        BeanUtils.copyProperties(this,entity);
        return entity;
    }

    public static HomBedBo convert(HomeBedEntity entity) {
        HomBedBo bo = new HomBedBo();
        BeanUtils.copyProperties(entity,bo);
        return bo;
    }


    public HomBedBVo convertVo() {
        HomBedBVo vo = new HomBedBVo();
        BeanUtils.copyProperties(this,vo);
        vo.setTime(time.format(YY_MM_DD));
        return vo;
    }
}
