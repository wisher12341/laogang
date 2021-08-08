package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.repository.entity.HomeBedEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

public class HomBedBo extends BaseBo{

    private Integer id;

    private Integer oldmanId;

    private String organ;

    private LocalDate time;


    public static HomBedBo convert(HomeBedEntity entity) {
        return null;
    }

    @Override
    public <Entity> Entity convert() {
        return null;
    }
}
