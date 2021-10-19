package com.lejian.laogang.controller.contract.request;

import com.google.common.collect.Lists;
import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.enums.label.LabelBaseEnum;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.LocationBo;
import com.lejian.laogang.repository.entity.OldmanAttrEntity;
import com.lejian.laogang.repository.entity.OldmanEntity;
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
                if (StringUtils.isNotBlank(v) && !v.equals("null")){
                    List<String> valueList = Lists.newArrayList(v.split(","));
                    valueList.forEach(value->{
                        OldmanAttrEntity entity = new OldmanAttrEntity();
                        entity.setValue(Integer.valueOf(value));
                        entity.setType(Integer.valueOf(type));
                        entity.setOldmanId(id);
                        entity.setIdCard(idCard);
                        list.add(entity);
                    });
                }else{
                    OldmanAttrEntity entity = new OldmanAttrEntity();
                    entity.setType(Integer.valueOf(type));
                    list.add(entity);
                }
            });
        }
        return list;
    }
}
