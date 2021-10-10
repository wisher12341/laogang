package com.lejian.laogang.controller.contract.request;

import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.util.StringUtils;
import lombok.Data;

@Data
public class PolicyParam {

    private String name;
    private String wh;
    private String content;
    private String startTime;
    private String endTime;
    private Integer type;

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
}
