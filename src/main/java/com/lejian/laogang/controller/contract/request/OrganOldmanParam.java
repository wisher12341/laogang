package com.lejian.laogang.controller.contract.request;

import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.repository.entity.OrganEntity;
import com.lejian.laogang.repository.entity.OrganOldmanEntity;
import com.lejian.laogang.util.DateUtils;
import com.lejian.laogang.util.StringUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

import static com.lejian.laogang.util.DateUtils.YY_MM_DD;

@Data
public class OrganOldmanParam {
    private Integer id;
    private String name;
    private String idCard;
    private String body;
    private String startTime;
    private String endTime;
    private String bed;
    private Integer organId;

    public JpaSpecBo convert() {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();

        jpaSpecBo.getEqualMap().put("status", 0);

        if (this.organId!=null){
            jpaSpecBo.getEqualMap().put("organId", this.organId);
        }

        return jpaSpecBo;
    }

    public OrganOldmanEntity convertEntity() {
        OrganOldmanEntity entity = new OrganOldmanEntity();
        BeanUtils.copyProperties(this,entity);
        if (StringUtils.isNotBlank(this.startTime)) {
            entity.setStartTime(DateUtils.stringToLocalDate(this.startTime,YY_MM_DD));
        }
        if (StringUtils.isNotBlank(this.endTime)) {
            entity.setEndTime(DateUtils.stringToLocalDate(this.endTime,YY_MM_DD));
        }
        return entity;
    }
}
