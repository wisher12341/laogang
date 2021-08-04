package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.pojo.vo.OldmanVo;
import com.lejian.laogang.repository.entity.OldmanEntity;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    private Integer floor;
    private LocalDate birthday;
    private String phone;
    private String country;
    private BusinessEnum family;
    private String idCard;
    private String areaCountry;
    private String areaTown;
    private String areaVillage;
    private String areaCustomOne;
    private Integer politics;
    private BusinessEnum householdType;
    private BusinessEnum education;
    private String landlineNumber;
    private BusinessEnum income;
    private String vaccine;
    private LocalDate vaccineFirst;
    private LocalDate vaccineSec;
    private String commercialInsurance;
    private String bloodiness;
    private BusinessEnum eyesight;
    private BusinessEnum psychosis;
    private LocationBo locationBo;

    public static OldmanBo convert(OldmanEntity entity) {
        OldmanBo oldmanBo = new OldmanBo();
        BeanUtils.copyProperties(entity,oldmanBo);
        oldmanBo.setMale(BusinessEnum.find(entity.getMale(), OldmanEnum.Male.class));
        return oldmanBo;
    }

    @Override
    public OldmanEntity convert() {
        return null;
    }

    public OldmanVo convertVo(){
        OldmanVo vo = new OldmanVo();
        BeanUtils.copyProperties(this,vo);
        vo.setAge(DateUtils.birthdayToAge(this.getBirthday()));
        vo.setMale(this.male.getDesc());
        return vo;
    }
}
