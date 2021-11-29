package com.lejian.laogang.pojo.vo;

import com.lejian.laogang.controller.contract.request.IntelligentDeviceParam;
import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.pojo.bo.LocationBo;
import com.lejian.laogang.repository.entity.OldmanEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class OldmanVo {
    private Integer id;
    private String male;
    private String name;
    private String idName;
    private String huji;
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
    private LocationBo locationBo;
    private String birthday;
    private Map<String,String> typeMap;
    private String war;
    private Integer ideviceId;
    private String  ideviceName;
    private String ideviceStartTime;
    private String ideviceEndTime;
    private Integer homeBedId;
    private String homeBedOrgan;
    private String homeBedTime;
    private Integer homeDoctorId;
    private String  homeDoctorName;
    private String homeDoctorOrgan;
    private String homeDoctorTime;

    private String lng;
    private String lat;

    private String address;
    private String rh;
}
