package com.lejian.laogang.controller.contract.request;


import com.lejian.laogang.enums.label.LabelBaseEnum;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

@Data
public class LinkManParam {


    private Integer id;

    private String name;

    private String phone;

    private String relation;

    private String idCard;

    private Integer oldmanId;

    public JpaSpecBo convert() {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        if (oldmanId!=null) {
            jpaSpecBo.getEqualMap().put("oldmanId",oldmanId);
        }
        return jpaSpecBo;
    }
}
