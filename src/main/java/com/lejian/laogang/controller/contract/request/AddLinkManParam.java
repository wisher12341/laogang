package com.lejian.laogang.controller.contract.request;


import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.repository.entity.LinkManEntity;
import com.lejian.laogang.util.AESUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class AddLinkManParam {


    private Integer iscall;
    private String name;

    private String phone;

    private String relation;

    private String idCard;

    private Integer oldmanId;

    public LinkManEntity convert() {
        LinkManEntity entity = new LinkManEntity();
        BeanUtils.copyProperties(this,entity);
        entity.setOldmanId(this.oldmanId);
        AESUtils.encode(entity);
        return entity;

    }
}
