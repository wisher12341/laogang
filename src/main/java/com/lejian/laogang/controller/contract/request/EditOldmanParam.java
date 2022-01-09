package com.lejian.laogang.controller.contract.request;

import com.google.common.collect.Lists;
import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.enums.label.LabelBaseEnum;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.LocationBo;
import com.lejian.laogang.repository.entity.*;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.util.Pair;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class EditOldmanParam {
    private Integer id;
    private Integer male;
    private String name;
    private Integer huji;
    private String birthday;
    private String residence;
    private String hujiAddress;
    private Integer homeowner;
    private Integer houseType;
    private Integer floor;
    private String phone;
    private String country;
    private String idCard;
    private String areaCountry;
    private String areaTown;
    private String areaVillage;
    private String areaCustomOne;
    private Integer politics;
    private Integer education;
    private String landlineNumber;
    private Integer income;
    private String vaccine;
    private String vaccineFirst;
    private String vaccineSec;
    private String commercialInsurance;
    private String bloodiness;
    private Integer eyesight;
    private Integer psychosis;
    private Integer isZd;
    private String war;
    private Map<String,String> map;
    private Map<String,String> ext;

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
    private Integer rh;
    private Integer status;

    public OldmanEntity convertEntity() {
        OldmanEntity oldmanEntity = new OldmanEntity();
        BeanUtils.copyProperties(this,oldmanEntity);
        if (StringUtils.isNotBlank(birthday)) {
            oldmanEntity.setBirthday(DateUtils.stringToLocalDate(birthday, YY_MM_DD));
        }
        return oldmanEntity;
    }

    public List<OldmanAttrEntity> convertAttrEntity() {
        List<OldmanAttrEntity> list = Lists.newArrayList();
        if (MapUtils.isNotEmpty(map)){
            map.forEach((k,v)->{
                String type = k.substring(4);
                String e=null;
                if (ext.containsKey("type"+type)){
                    e = ext.get("type"+type) ;
                }

                if (StringUtils.isNotBlank(v) && !v.equals("null")){
                    List<String> valueList = Lists.newArrayList(v.split(","));
                    String finalE = e;
                    valueList.forEach(value->{
                        OldmanAttrEntity entity = new OldmanAttrEntity();
                        entity.setValue(Integer.valueOf(value));
                        entity.setType(Integer.valueOf(type));
                        entity.setOldmanId(id);
                        entity.setIdCard(idCard);
                        if(ext.containsKey("type"+type+"_"+value)){
                            entity.setExt(ext.get("type"+type+"_"+value));
                        }else {
                            entity.setExt(finalE);
                        }
                        list.add(entity);
                    });
                } else{
                    OldmanAttrEntity entity = new OldmanAttrEntity();
                    entity.setType(Integer.valueOf(type));
                    entity.setOldmanId(id);
                    entity.setIdCard(idCard);
                    list.add(entity);
                }
            });
        }
        return list;
    }

    public IntelligentDeviceEntity convertId() {
        if(StringUtils.isBlank(ideviceName)){
            return null;
        }
        IntelligentDeviceEntity entity = new IntelligentDeviceEntity();
        entity.setId(ideviceId);
        entity.setOldmanId(id);
        entity.setName(ideviceName);
        if (StringUtils.isNotBlank(ideviceStartTime)) {
            entity.setStartTime(DateUtils.stringToLocalDate(ideviceStartTime, YY_MM_DD));
        }
        if (StringUtils.isNotBlank(ideviceEndTime)) {
            entity.setEndTime(DateUtils.stringToLocalDate(ideviceEndTime, YY_MM_DD));
        }
        return entity;
    }

    public HomeBedEntity convertHomeBed() {
        if (StringUtils.isBlank(homeBedOrgan)){
            return null;
        }
        HomeBedEntity entity = new HomeBedEntity();
        entity.setOldmanId(id);
        entity.setOrgan(homeBedOrgan);
        entity.setId(homeBedId);
        if (StringUtils.isNotBlank(homeBedTime)) {
            entity.setTime(DateUtils.stringToLocalDate(homeBedTime, YY_MM_DD));
        }
        return entity;
    }

    public HomeDoctorEntity convertHomeDoctor() {
        if (StringUtils.isBlank(homeDoctorName)){
            return null;
        }
        HomeDoctorEntity entity = new HomeDoctorEntity();
        entity.setId(homeDoctorId);
        entity.setName(homeDoctorName);
        entity.setOrgan(homeDoctorOrgan);
        entity.setOldmanId(id);
        if (StringUtils.isNotBlank(homeDoctorTime)) {
            entity.setTime(DateUtils.stringToLocalDate(homeDoctorTime, YY_MM_DD));
        }
        return entity;
    }
}
