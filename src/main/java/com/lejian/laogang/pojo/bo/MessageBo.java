package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.controller.contract.request.PolicyParam;
import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.MessageEnum;
import com.lejian.laogang.pojo.vo.MessageVo;
import com.lejian.laogang.repository.entity.MessageEntity;
import com.lejian.laogang.repository.entity.PolicyEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class MessageBo extends BaseBo{
    private Integer id;


    private String content;


    private Integer userId;

    private Integer roleId;
    private String sender;
    private Integer type;

    @Override
    public MessageEntity convert() {
        MessageEntity messageEntity = new MessageEntity();
        BeanUtils.copyProperties(this,messageEntity);
        return messageEntity;
    }

    public static MessageBo convert(MessageEntity messageEntity) {
        MessageBo messageBo = new MessageBo();
        BeanUtils.copyProperties(messageEntity,messageBo);
        return messageBo;
    }

    public MessageVo convertVo(){
        MessageVo vo = new MessageVo();
        BeanUtils.copyProperties(this,vo);
        vo.setType(BusinessEnum.find(this.type, MessageEnum.Type.class).getDesc());
        return vo;
    }
}
