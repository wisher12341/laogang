package com.lejian.laogang.controller.contract.request;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class OldmanParam {

    private String name;
    private String idCard;
    private Integer serviceStatus;
    private String createTimeStart;
    private String createTimeEnd;
    private String areaCustomOne;
    private String areaCountry;
    private String areaTown;
    private String areaVillage;
    private Integer status;
    private String birthdayLike;
    private String male;
    /**
     * 年龄范围
     */
    private String age;
    private String householdType;
    private String familyType;
    /**
     * 重点老人
     */
    private Boolean isZd;




    public JpaSpecBo convert() {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        jpaSpecBo.getEqualMap().put("status",0);


        if(StringUtils.isNotBlank(this.getAreaCustomOne())){
            jpaSpecBo.getEqualMap().put("areaCustomOne", this.getAreaCustomOne());
        }
        if (StringUtils.isNotBlank(this.getAreaVillage())){
            jpaSpecBo.getEqualMap().put("areaVillage", this.getAreaVillage());
        }
        if (StringUtils.isNotBlank(this.getAreaTown())){
            jpaSpecBo.getEqualMap().put("areaTown", this.getAreaTown());
        }
        if (StringUtils.isNotBlank(this.getAreaCountry())){
            jpaSpecBo.getEqualMap().put("areaCountry", this.getAreaCountry());
        }
        if (StringUtils.isNotBlank(this.getName())){
            jpaSpecBo.getLikeMap().put("name","%"+this.getName()+"%");
        }

        if (StringUtils.isNotBlank(this.getIdCard())){
            jpaSpecBo.getEqualMap().put("idCard", this.getIdCard());
        }

        if (StringUtils.isNotBlank(this.getCreateTimeStart())){
            jpaSpecBo.getGreatEMap().put("createTime", Timestamp.valueOf(this.getCreateTimeStart()));
        }

        if (StringUtils.isNotBlank(this.getCreateTimeEnd())){
            jpaSpecBo.getLessEMap().put("createTime", Timestamp.valueOf(this.getCreateTimeEnd()));
        }


        if(StringUtils.isNotBlank(this.getBirthdayLike())){
            jpaSpecBo.getLikeMap().put("birthday","%"+this.getBirthdayLike()+"%");
        }

        if(StringUtils.isNotBlank(this.getMale())){
            jpaSpecBo.getEqualMap().put("male", BusinessEnum.find(this.getMale(), OldmanEnum.Male.class).getValue());
        }

        if(StringUtils.isNotBlank(this.getAge())){
            String start=this.getAge().split("-")[0];
            String end="";
            if(this.getAge().split("-").length>1) {
                end = this.getAge().split("-")[1];
            }
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            if(StringUtils.isNotBlank(end)) {
                jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)).toLocalDate());
            }
        }

        if (this.isZd!=null && this.isZd){
            jpaSpecBo.getEqualMap().put("isZd","1");
        }
        return jpaSpecBo;
    }
}
