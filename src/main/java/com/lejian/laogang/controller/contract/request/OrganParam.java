package com.lejian.laogang.controller.contract.request;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.enums.label.LabelBaseEnum;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class OrganParam {
    private Integer type;

    public JpaSpecBo convert() {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();

        jpaSpecBo.getEqualMap().put("status", 0);

        if (this.type!=null){
            jpaSpecBo.getEqualMap().put("type", this.type);
        }

        return jpaSpecBo;
    }
}
