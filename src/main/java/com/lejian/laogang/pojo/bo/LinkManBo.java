package com.lejian.laogang.pojo.bo;


import com.lejian.laogang.pojo.vo.LinkManVo;
import com.lejian.laogang.repository.entity.LinkManEntity;
import com.lejian.laogang.util.AESUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
public class LinkManBo  extends BaseBo {


    private Integer id;

    private String name;

    private String phone;

    private String relation;

    private String idCard;
    private Integer iscall;

    @Override
    public LinkManEntity convert() {
        LinkManEntity entity = new LinkManEntity();
        BeanUtils.copyProperties(this,entity);
        AESUtils.encode(entity);
        return entity;
    }

    public static LinkManBo convert(LinkManEntity entity) {
        AESUtils.decode(entity);
        LinkManBo bo = new LinkManBo();
        BeanUtils.copyProperties(entity,bo);
        return bo;
    }

    public static LinkManVo convertVo(LinkManBo linkManBo) {
        LinkManVo vo = new LinkManVo();
        BeanUtils.copyProperties(linkManBo,vo);
        if (linkManBo.getIscall()==1){
            vo.setIscall("是");
        }else{
            vo.setIscall("否");
        }
        return vo;
    }
}
