package com.lejian.laogang.pojo.vo;

import com.lejian.laogang.controller.contract.request.PolicyParam;
import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.repository.entity.PolicyEntity;
import com.lejian.laogang.util.DateUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class PolicyVo extends BaseBo{
    private Integer id;

    private String name;

    private String content;
    private String type;

    private String wh;


    private LocalDate startTime;
    private LocalDate endTime;

    @Override
    public PolicyEntity convert() {
        PolicyEntity policyEntity = new PolicyEntity();
        BeanUtils.copyProperties(this,policyEntity);
        return policyEntity;
    }

    public static PolicyVo convert(PolicyEntity policyEntity) {
        PolicyVo policyBo = new PolicyVo();
        BeanUtils.copyProperties(policyEntity,policyBo);
        return policyBo;
    }

    public static PolicyVo convert(PolicyParam policyParam) {
        PolicyVo policyBo = new PolicyVo();
        BeanUtils.copyProperties(policyParam,policyBo);
        policyBo.setStartTime(DateUtils.stringToLocalDate(policyParam.getStartTime(),YY_MM_DD));
        policyBo.setEndTime(DateUtils.stringToLocalDate(policyParam.getEndTime(),YY_MM_DD));
        return policyBo;
    }
}
