package com.lejian.laogang.pojo.vo;

import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HomeDoctorVo{
    private Integer id;
    private Integer oldmanId;

    private String  name;

    private String organ;

    private String time;
}
