package com.lejian.laogang.pojo.vo;

import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HomBedBVo {

    private Integer id;

    private Integer oldmanId;

    private String organ;

    private String time;

}
