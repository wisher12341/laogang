package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.repository.entity.OldmanEntity;
import com.lejian.laogang.repository.entity.OldmanViewEntity;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.lejian.laogang.util.DateUtils.YYMMDDHHMMSS;
import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class OldmanBo extends BaseBo {
    private Integer id;
    private BusinessEnum male;
    private String name;
    private BusinessEnum huji;
    private String residence;
    private String hujiAddress;
    private BusinessEnum homeowner;
    private BusinessEnum houseType;
    private String floor;
    private LocalDate birthday;
    private String phone;
    private String country;
    private BusinessEnum family;
    private String idCard;
    private String areaCountry;
    private String areaTown;
    private String areaVillage;
    private String areaCustomOne;
    private BusinessEnum politics;
    private BusinessEnum householdType;
    private BusinessEnum education;
    private String landlineNumber;
    private BusinessEnum income;
    private String vaccine;
    private String vaccineFirst;
    private String vaccineSec;
    private String commercialInsurance;
    private String bloodiness;
    private BusinessEnum eyesight;
    private BusinessEnum psychosis;
    private LocationBo locationBo;
    private Integer status;
    private Integer locationId;
    private String war;

    private String lng;
    private String lat;
    private String gpsDesc;
    private Timestamp datachangeTime;

    public static OldmanBo convert(OldmanEntity entity) {
        OldmanBo oldmanBo = new OldmanBo();
        BeanUtils.copyProperties(entity,oldmanBo);
        oldmanBo.setMale(BusinessEnum.find(entity.getMale(), OldmanEnum.Male.class));
        if (entity.getHuji()!=null) oldmanBo.setHuji(BusinessEnum.find(entity.getHuji(), OldmanEnum.Huji.class));
        if (entity.getHomeowner()!=null) oldmanBo.setHomeowner(BusinessEnum.find(entity.getHomeowner(), OldmanEnum.HOMEOWNER.class));
        if (entity.getHouseType()!=null) oldmanBo.setHouseType(BusinessEnum.find(entity.getHouseType(), OldmanEnum.HOSE_TYPE.class));
        if (entity.getEyesight()!=null) oldmanBo.setEyesight(BusinessEnum.find(entity.getEyesight(), OldmanEnum.eyesight.class));
        if (entity.getFloor()!=null) oldmanBo.setFloor(entity.getFloor().toString());
        if (entity.getPolitics()!=null) oldmanBo.setPolitics(BusinessEnum.find(entity.getPolitics(), OldmanEnum.politics.class));
        if (entity.getEducation()!=null) oldmanBo.setEducation(BusinessEnum.find(entity.getEducation(), OldmanEnum.education.class));
        if (entity.getIncome()!=null) oldmanBo.setIncome(BusinessEnum.find(entity.getIncome(), OldmanEnum.income.class));
        if (entity.getPsychosis()!=null) oldmanBo.setPsychosis(BusinessEnum.find(entity.getPsychosis(), OldmanEnum.psychosis.class));
        return oldmanBo;
    }

    public static OldmanBo convert(OldmanViewEntity entity) {
        OldmanBo oldmanBo = new OldmanBo();
        BeanUtils.copyProperties(entity,oldmanBo);
        oldmanBo.setMale(BusinessEnum.find(entity.getMale(), OldmanEnum.Male.class));
        return oldmanBo;
    }

    @Override
    public OldmanEntity convert() {
        OldmanEntity entity = new OldmanEntity();
        BeanUtils.copyProperties(this,entity);
        if (male!=null) entity.setMale(male.getValue());
        if (huji!=null) entity.setHuji(huji.getValue());
        if (homeowner!=null) entity.setHomeowner(homeowner.getValue());
        if (houseType!=null) entity.setHouseType(houseType.getValue());
        if (eyesight!=null) entity.setEyesight(eyesight.getValue());
        if (StringUtils.isNotBlank(floor)) entity.setFloor(Integer.valueOf(floor));
        if (politics!=null) entity.setPolitics(politics.getValue());
        if (education!=null) entity.setEducation(education.getValue());
        if (income!=null) entity.setIncome(income.getValue());
        if (psychosis!=null) entity.setPsychosis(psychosis.getValue());
        return entity;
    }

    public OldmanVo convertVo(){
        OldmanVo vo = new OldmanVo();
        BeanUtils.copyProperties(this,vo);
        vo.setAge(DateUtils.birthdayToAge(this.getBirthday()));
        vo.setBirthday(this.getBirthday().format(YY_MM_DD));
        vo.setIdName(id+"_"+name);
        if (male!=null) vo.setMale(male.getDesc());
        if (huji!=null) vo.setHuji(huji.getDesc());
        if (homeowner!=null) vo.setHomeowner(homeowner.getDesc());
        if (houseType!=null) vo.setHouseType(houseType.getDesc());
        if (eyesight!=null) vo.setEyesight(eyesight.getDesc());
        if (StringUtils.isNotBlank(floor)) vo.setFloor(Integer.valueOf(floor));
        if (politics!=null) vo.setPolitics(politics.getDesc());
        if (education!=null) vo.setEducation(education.getDesc());
        if (income!=null) vo.setIncome(income.getDesc());
        if (psychosis!=null) vo.setPsychosis(psychosis.getDesc());
        return vo;
    }
}
