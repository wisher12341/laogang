package com.lejian.laogang.controller.contract.request;

import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.PolicyBo;
import com.lejian.laogang.util.DateUtils;
import com.lejian.laogang.util.StringUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class PolicyParam {

    private String name;
    private String wh;
    private String content;
    private String startTime;
    private String endTime;
    private Integer type;
    private Integer id;

    public JpaSpecBo convert() {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        if (StringUtils.isNotBlank(name)){
            jpaSpecBo.getLikeMap().put("name","%"+name+"%");
        }
        if (StringUtils.isNotBlank(wh)){
            jpaSpecBo.getEqualMap().put("wh",wh);
        }
        if (type!=null){
            jpaSpecBo.getEqualMap().put("type",type);
        }
        if (StringUtils.isNotBlank(content)){
            jpaSpecBo.getLikeMap().put("content","%"+content+"%");
        }
        return jpaSpecBo;
    }

    public PolicyBo convertToBo() {
        PolicyBo policyBo = new PolicyBo();
        BeanUtils.copyProperties(this,policyBo);
        if (StringUtils.isNotBlank(this.getStartTime())) {
            policyBo.setStartTime(DateUtils.stringToLocalDate(this.getStartTime(),DateUtils.YY_MM_DD));
        }
        if (StringUtils.isNotBlank(this.getEndTime())) {
            policyBo.setEndTime(DateUtils.stringToLocalDate(this.getEndTime(),DateUtils.YY_MM_DD));
        }
        return policyBo;
    }
}
