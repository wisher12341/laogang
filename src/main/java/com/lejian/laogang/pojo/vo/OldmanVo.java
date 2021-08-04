package com.lejian.laogang.pojo.vo;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.pojo.bo.LocationBo;
import com.lejian.laogang.repository.entity.OldmanEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OldmanVo extends BaseBo {
    private Integer id;
    private String male;
    private String name;
    private BusinessEnum huji;
    private String residence;
    private String hujiAddress;
    private String homeowner;
    private String houseType;
    private Integer floor;
    private Integer age;
    private String phone;
    private String country;
    private String family;
    private String idCard;
    private String areaCountry;
    private String areaTown;
    private String areaVillage;
    private String areaCustomOne;
    private Integer politics;
    private String householdType;
    private String education;
    private String landlineNumber;
    private String income;
    private String vaccine;
    private LocalDate vaccineFirst;
    private LocalDate vaccineSec;
    private String commercialInsurance;
    private String bloodiness;
    private String eyesight;
    private String psychosis;
    private LocationBo locationBo;

    public static OldmanVo convert(OldmanEntity entity) {
        return null;
    }

    @Override
    public <Entity> Entity convert() {
        return null;
    }
}
