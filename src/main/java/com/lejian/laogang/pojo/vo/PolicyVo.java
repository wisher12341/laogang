package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.controller.contract.request.PolicyParam;
import com.lejian.laogang.repository.entity.OrganEntity;
import com.lejian.laogang.repository.entity.PolicyEntity;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class PolicyBo extends BaseBo{
    private Integer id;

    private String name;

    private String content;
    private Integer type;

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
        policyBo.setStartTime(DateUtils.stringToLocalDate(policyParam.getStartTime(),YY_MM_DD));
        policyBo.setEndTime(DateUtils.stringToLocalDate(policyParam.getEndTime(),YY_MM_DD));
        return policyBo;
    }
}
