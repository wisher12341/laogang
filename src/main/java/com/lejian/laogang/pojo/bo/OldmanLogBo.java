package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.repository.entity.OldmanEntity;
import com.lejian.laogang.repository.entity.OldmanViewEntity;
import com.lejian.laogang.util.AESUtils;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.time.LocalDate;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class OldmanLogBo {
    private Integer id;
    private String male;
    private String name;
    private String huji;
    private String residence;
    private String hujiAddress;
    private String homeowner;
    private String houseType;
    private Integer floor;
    private String birthday;
    private String phone;
    private String country;
    private String family;
    private String idCard;
    private String areaCountry;
    private String areaTown;
    private String areaVillage;
    private String areaCustomOne;
    private String politics;
    private String householdType;
    private String education;
    private String landlineNumber;
    private String income;
    private String vaccine;
    private String vaccineFirst;
    private String vaccineSec;
    private String commercialInsurance;
    private String bloodiness;
    private String eyesight;
    private String psychosis;
    private String war;
    private String address;
    private String rh;
    private Integer userId;
    private String status;
}
