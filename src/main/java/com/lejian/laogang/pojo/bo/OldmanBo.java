package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.repository.entity.LinkManEntity;
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

    private String address;
    private BusinessEnum rh;
    private Integer userId;


    public static OldmanBo convert(OldmanEntity entity) {
        //在事务中，查询到值后，set操作修改entity的值，会自动Update更新相关字段，所以我们要新New 一个entity避免这个问题
        OldmanEntity newEntity = new OldmanEntity();
        BeanUtils.copyProperties(entity,newEntity);
        AESUtils.decode(newEntity);

        OldmanBo oldmanBo = new OldmanBo();
        BeanUtils.copyProperties(newEntity,oldmanBo);
        oldmanBo.setMale(BusinessEnum.find(newEntity.getMale(), OldmanEnum.Male.class));
        if (newEntity.getHuji()!=null) oldmanBo.setHuji(BusinessEnum.find(newEntity.getHuji(), OldmanEnum.Huji.class));
        if (newEntity.getHomeowner()!=null) oldmanBo.setHomeowner(BusinessEnum.find(newEntity.getHomeowner(), OldmanEnum.HOMEOWNER.class));
        if (newEntity.getHouseType()!=null) oldmanBo.setHouseType(BusinessEnum.find(newEntity.getHouseType(), OldmanEnum.HOSE_TYPE.class));
        if (newEntity.getEyesight()!=null) oldmanBo.setEyesight(BusinessEnum.find(newEntity.getEyesight(), OldmanEnum.eyesight.class));
        if (newEntity.getFloor()!=null) oldmanBo.setFloor(newEntity.getFloor().toString());
        if (newEntity.getPolitics()!=null) oldmanBo.setPolitics(BusinessEnum.find(newEntity.getPolitics(), OldmanEnum.politics.class));
        if (newEntity.getEducation()!=null) oldmanBo.setEducation(BusinessEnum.find(newEntity.getEducation(), OldmanEnum.education.class));
        if (newEntity.getIncome()!=null) oldmanBo.setIncome(BusinessEnum.find(newEntity.getIncome(), OldmanEnum.income.class));
        if (newEntity.getPsychosis()!=null) oldmanBo.setPsychosis(BusinessEnum.find(newEntity.getPsychosis(), OldmanEnum.psychosis.class));
        if (newEntity.getRh()!=null) oldmanBo.setRh(BusinessEnum.find(newEntity.getRh(),OldmanEnum.RH.class));
        return oldmanBo;
    }

    public static OldmanBo convert(OldmanViewEntity entity) {
        //在事务中，查询到值后，set操作修改entity的值，会自动Update更新相关字段，所以我们要新New 一个entity避免这个问题
        OldmanViewEntity newEntity = new OldmanViewEntity();
        BeanUtils.copyProperties(entity,newEntity);

        AESUtils.decode(newEntity);
        OldmanBo oldmanBo = new OldmanBo();
        BeanUtils.copyProperties(newEntity,oldmanBo);
        oldmanBo.setMale(BusinessEnum.find(newEntity.getMale(), OldmanEnum.Male.class));
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
        if (rh!=null) entity.setRh(rh.getValue());
        AESUtils.encode(entity);
        return entity;
    }

    public OldmanVo convertVo(){
        OldmanVo vo = new OldmanVo();
        BeanUtils.copyProperties(this,vo);
        vo.setAge(DateUtils.birthdayToAge(this.getBirthday()));
        if (this.getBirthday()!=null) {
            vo.setBirthday(this.getBirthday().format(YY_MM_DD));
        }
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
        if (rh!=null) vo.setRh(rh.getDesc());
        if (status!=null) {
            vo.setStatus(status == 0 ? "正常" : status == 2 ? "死亡" : "");
        }
        return vo;
    }

    public OldmanVo convertVo(PolicyOldmanBo policyOldmanBo){
        OldmanVo vo = convertVo();
        vo.setFinish(policyOldmanBo.getFinish()==1?"是":"否");
        vo.setIdFinish(vo.getId()+"_"+policyOldmanBo.getFinish());
        return vo;
    }
}
