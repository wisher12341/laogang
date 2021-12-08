package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.controller.contract.request.PolicyParam;
import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.PolicyEnum;
import com.lejian.laogang.pojo.vo.PolicyVo;
import com.lejian.laogang.repository.entity.OrganEntity;
import com.lejian.laogang.repository.entity.PolicyEntity;
import com.lejian.laogang.util.DateUtils;
import com.lejian.laogang.util.StringUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.lejian.laogang.util.DateUtils.YYMMDDHHMMSS;
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
    private LocalDateTime createTime;

    @Override
    public PolicyEntity convert() {
        PolicyEntity policyEntity = new PolicyEntity();
        BeanUtils.copyProperties(this,policyEntity);
        return policyEntity;
    }

    public PolicyVo convertVo() {
        PolicyVo policyVo = new PolicyVo();
        BeanUtils.copyProperties(this,policyVo);
        policyVo.setType(BusinessEnum.find(type, PolicyEnum.Type.class).getDesc());
        if (this.startTime!=null)
            policyVo.setStartTime(this.startTime.format(YY_MM_DD));
        if (this.endTime!=null)
            policyVo.setEndTime(this.endTime.format(YY_MM_DD));
        if (this.createTime!=null)
            policyVo.setCreateTime(this.createTime.format(YYMMDDHHMMSS));
        return policyVo;
    }

    public static PolicyBo convert(PolicyEntity policyEntity) {
        PolicyBo policyBo = new PolicyBo();
        BeanUtils.copyProperties(policyEntity,policyBo);
        if (policyEntity.getCreateTime()!=null)
        policyBo.setCreateTime(policyEntity.getCreateTime().toLocalDateTime());
        return policyBo;
    }

    public static PolicyBo convert(PolicyParam policyParam) {
        PolicyBo policyBo = new PolicyBo();
        BeanUtils.copyProperties(policyParam,policyBo);
        if (StringUtils.isNotBlank(policyParam.getStartTime()))
        policyBo.setStartTime(DateUtils.stringToLocalDate(policyParam.getStartTime(),YY_MM_DD));
        if (StringUtils.isNotBlank(policyParam.getEndTime()))
        policyBo.setEndTime(DateUtils.stringToLocalDate(policyParam.getEndTime(),YY_MM_DD));
        return policyBo;
    }
}
