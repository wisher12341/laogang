package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.controller.contract.request.PolicyParam;
import com.lejian.laogang.repository.entity.OrganEntity;
import com.lejian.laogang.repository.entity.PolicyEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class PolicyBo extends BaseBo{
    private Integer id;

    private String name;

    private String content;


    private String wh;


    private LocalDate startTime;
    private LocalDate endTime;

    @Override
    public PolicyEntity convert() {
        PolicyEntity policyEntity = new PolicyEntity();
        BeanUtils.copyProperties(this,policyEntity);
        return policyEntity;
    }

    public static PolicyBo convert(PolicyEntity policyEntity) {
        PolicyBo policyBo = new PolicyBo();
        BeanUtils.copyProperties(policyEntity,policyBo);
        return policyBo;
    }

    public static PolicyBo convert(PolicyParam policyParam) {
        PolicyBo policyBo = new PolicyBo();
        BeanUtils.copyProperties(policyParam,policyBo);
        return policyBo;
    }
}
