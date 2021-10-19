package com.lejian.laogang.pojo.vo;


import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.repository.entity.LinkManEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class LinkManVo {


    private Integer id;

    private String name;

    private String phone;

    private String relation;

    private String idCard;

    private String iscall;

}
