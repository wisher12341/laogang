package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.controller.contract.request.PolicyParam;
import com.lejian.laogang.repository.entity.PolicyEntity;
import com.lejian.laogang.repository.entity.PolicyOldmanEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
public class PolicyOldmanBo extends BaseBo{
    private Integer id;

    private Integer oldmanId;

    private Integer policyId;


    @Override
    public PolicyOldmanEntity convert() {
        PolicyOldmanEntity policyOldmanEntity = new PolicyOldmanEntity();
        BeanUtils.copyProperties(this,policyOldmanEntity);
        return policyOldmanEntity;
    }

    public static PolicyOldmanBo convert(PolicyOldmanEntity policyOldmanEntity) {
        PolicyOldmanBo policyOldmanBo = new PolicyOldmanBo();
        BeanUtils.copyProperties(policyOldmanEntity,policyOldmanBo);
        return policyOldmanBo;
    }

}
