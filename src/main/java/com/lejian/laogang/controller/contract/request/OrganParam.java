package com.lejian.laogang.controller.contract.request;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.enums.label.LabelBaseEnum;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.repository.entity.OrganEntity;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class OrganParam {
    private Integer type;

    private String name;
    private String descri;
    private String service;
    private String address;
    private String web;
    private String email;
    private String phone;
    private String workerTime;
    private String ssqk;
    private String rzyq;
    private Integer bedNumber;
    private Integer id;

    public JpaSpecBo convert() {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();

        jpaSpecBo.getEqualMap().put("status", 0);

        if (this.type!=null){
            jpaSpecBo.getEqualMap().put("type", this.type);
        }

        return jpaSpecBo;
    }

    public OrganEntity convertEntity() {
        OrganEntity entity = new OrganEntity();
        BeanUtils.copyProperties(this,entity);
        return entity;
    }
}
