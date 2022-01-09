package com.lejian.laogang.controller.contract.request;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.enums.UserEnum;
import com.lejian.laogang.enums.label.LabelBaseEnum;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.UserBo;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.util.Pair;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class OldmanParam {
    //是否是政策生成的
    private Boolean isPolicy;

    private String search;
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
    private String isZd;
    private String rh;

    private List<String> labelIdList;

    private Boolean isView = false;


    //与
    private List<String> politicsList;
    private String huiji;
    //与
    private List<String> eyesightList;
    //与
    private List<String> psychosisList;
    //与
    private List<String> jkzkList;
    //与
    private List<String> familyList;
    //与
    private List<String> familyTypeList;
    //与
    private List<String> incomeList;
    //与
    private List<String> areaVillageList;
    private List<String> jujiaList;
    private List<String> serviceStatusList;
    private List<String> shequList;
    private List<String> jigouList;

    //是否有家庭医生 1有 2没有
    private Integer haveDoctor;

    public JpaSpecBo convert() {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        if (CollectionUtils.isNotEmpty(labelIdList)) {
            jpaSpecBo = LabelBaseEnum.generateJpaSpecBo(labelIdList);
        }

        if (StringUtils.isNotBlank(this.getAreaCustomOne())) {
            jpaSpecBo.getEqualMap().put("areaCustomOne", this.getAreaCustomOne());
        }
        if (StringUtils.isNotBlank(this.getAreaVillage())) {
            jpaSpecBo.getEqualMap().put("areaVillage", this.getAreaVillage());
        }
        if (StringUtils.isNotBlank(this.getAreaTown())) {
            jpaSpecBo.getEqualMap().put("areaTown", this.getAreaTown());
        }
        if (StringUtils.isNotBlank(this.getAreaCountry())) {
            jpaSpecBo.getEqualMap().put("areaCountry", this.getAreaCountry());
        }
        if (StringUtils.isNotBlank(this.getName())) {
            jpaSpecBo.getLikeMap().put("name", "%" + this.getName() + "%");
        }

        if (StringUtils.isNotBlank(this.getIdCard())) {
            jpaSpecBo.getEqualMap().put("idCard", this.getIdCard());
        }

        if (StringUtils.isNotBlank(this.getCreateTimeStart())) {
            jpaSpecBo.getGreatEMap().put("createTime", Timestamp.valueOf(this.getCreateTimeStart()));
        }

        if (StringUtils.isNotBlank(this.getCreateTimeEnd())) {
            jpaSpecBo.getLessEMap().put("createTime", Timestamp.valueOf(this.getCreateTimeEnd()));
        }


        if (StringUtils.isNotBlank(this.getBirthdayLike())) {
            jpaSpecBo.getLikeMap().put("birthday", "%" + this.getBirthdayLike() + "%");
        }

        if (StringUtils.isNotBlank(this.getMale())) {
            jpaSpecBo.getEqualMap().put("male", BusinessEnum.find(this.getMale(), OldmanEnum.Male.class).getValue());
        }
        if (StringUtils.isNotBlank(this.getHuiji())) {
            jpaSpecBo.getEqualMap().put("huji", this.getHuiji());
        }
        if (StringUtils.isNotBlank(this.getRh())) {
            jpaSpecBo.getEqualMap().put("rh", this.getRh());
        }
        if (CollectionUtils.isNotEmpty(this.getPoliticsList())) {
            jpaSpecBo.getInMap().put("politics", new ArrayList<>(this.getPoliticsList()));
        }
        if (CollectionUtils.isNotEmpty(this.getEyesightList())) {
            jpaSpecBo.getInMap().put("eyesight", new ArrayList<>(this.getEyesightList()));
        }
        if (CollectionUtils.isNotEmpty(this.getPsychosisList())) {
            jpaSpecBo.getInMap().put("psychosis", new ArrayList<>(this.getPsychosisList()));
        }
        if (CollectionUtils.isNotEmpty(this.getAreaVillageList())) {
            jpaSpecBo.getInMap().put("areaVillage", new ArrayList<>(this.getAreaVillageList()));
        }
        if (StringUtils.isNotBlank(this.getAge())) {
            String start = this.getAge().split("-")[0];
            String end = "";
            if (this.getAge().split("-").length > 1) {
                end = this.getAge().split("-")[1];
            }
            if (StringUtils.isNotBlank(start)) {
                jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            }
            if (StringUtils.isNotBlank(end)) {
                jpaSpecBo.getGreatMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)+1).toLocalDate());
            }
        }

        if (StringUtils.isNotBlank(isZd)) {
            jpaSpecBo.getEqualMap().put("isZd", isZd);
        }
        if (haveDoctor!=null){
            if (haveDoctor==1){
                jpaSpecBo.getNotEqualMap().put("doctorId","0");
            }
            if (haveDoctor==2){
                jpaSpecBo.getEqualMap().put("doctorId","0");
            }
        }
        return jpaSpecBo;
    }

    public Pair<String, String> getSql(UserBo userBo){
        JpaSpecBo jpaSpecBo = this.convert();
        //村居角色
        if (userBo!=null && userBo.getRole().intValue() == UserEnum.Role.A2.getValue()){
            jpaSpecBo.getEqualMap().put("userId",userBo.getId());
        }

        String oldman = jpaSpecBo.getSql("o.");
        if (StringUtils.isNotBlank(search)){
            if (StringUtils.isNotBlank(oldman)){
                oldman+=" and ";
            }
            oldman+=" (o.name like '%"+search+"%' or o.id_card like '%"+search+"%' )";
        }
        StringBuilder attr = new StringBuilder();
        if (CollectionUtils.isNotEmpty(this.jkzkList)) {
            attr.append("(");
            for (int i=0;i<jkzkList.size();i++){
                if (i!=0){
                    attr.append(" and ");
                }
                attr.append(" oa.type ").append(" like ").append("'%").append("@").append(jkzkList.get(i)).append("_%'");
            }
            attr.append(")");
        }
        if (CollectionUtils.isNotEmpty(familyList)){
            if (attr.length()>0){
                attr.append(" and ");
            }
            attr.append("(");
            for (int i=0;i<familyList.size();i++){
                if (i!=0){
                    attr.append(" and ");
                }
                attr.append(" oa.type ").append(" like ").append("'%").append("@2_").append(familyList.get(i)).append("_%'");
            }


            attr.append(")");
        }
        if (CollectionUtils.isNotEmpty(familyTypeList)){
            if (attr.length()>0){
                attr.append(" and ");
            }
            attr.append("(");
            for (int i=0;i<familyTypeList.size();i++){
                if (i!=0){
                    attr.append(" and ");
                }
                attr.append(" oa.type ").append(" like ").append("'%").append("@3_").append(familyTypeList.get(i)).append("_%'");
            }

            attr.append(")");
        }
        if (CollectionUtils.isNotEmpty(incomeList)){
            if (attr.length()>0){
                attr.append(" and ");
            }
            attr.append("(");
            for (int i=0;i<incomeList.size();i++){
                if (i!=0){
                    attr.append(" and ");
                }
                attr.append(" oa.type ").append(" like ").append("'%").append("@4_").append(incomeList.get(i)).append("_%'");
            }
            attr.append(")");
        }
        if (serviceStatus!=null){
            if (attr.length()>0){
                attr.append(" and ");
            }

            attr.append(" oa.type ").append(" like ").append("'%").append("@13_").append(serviceStatus).append("_%'");

        }
        //养老状态 和 居家养老项目
        if (CollectionUtils.isNotEmpty(serviceStatusList) || CollectionUtils.isNotEmpty(jujiaList)
                || CollectionUtils.isNotEmpty(shequList) || CollectionUtils.isNotEmpty(jigouList)){
            if (attr.length()>0){
                attr.append(" and ");
            }
            attr.append("(");
            for (int i=0;i<serviceStatusList.size();i++){
                if (i!=0){
                    attr.append(" and ");
                }
                attr.append(" oa.type ").append(" like ").append("'%").append("@13_").append(serviceStatusList.get(i)).append("_%'");
            }
            if (CollectionUtils.isNotEmpty(jujiaList)) {
                attr.append(" and ");
                for (int i = 0; i < jujiaList.size(); i++) {
                    if (i != 0) {
                        attr.append(" and ");
                    }
                    attr.append(" oa.type ").append(" like ").append("'%").append("@14_").append(jujiaList.get(i)).append("_%'");
                }
            }
            if (CollectionUtils.isNotEmpty(jigouList)) {
                attr.append(" and ");
                for (int i = 0; i < jigouList.size(); i++) {
                    if (i != 0) {
                        attr.append(" and ");
                    }
                    attr.append(" oa.type ").append(" like ").append("'%").append("@13_1_").append(jigouList.get(i)).append("%'");
                }
            }
            if (CollectionUtils.isNotEmpty(shequList)) {
                attr.append(" and ");
                for (int i = 0; i < shequList.size(); i++) {
                    if (i != 0) {
                        attr.append(" and ");
                    }
                    attr.append(" oa.type ").append(" like ").append("'%").append("@13_2_").append(shequList.get(i)).append("%'");
                }
            }
            attr.append(")");
        }
        return Pair.of(oldman, attr.toString());
    }

    public Pair<String, String> getSql() {
        return getSql(null);
    }
}
