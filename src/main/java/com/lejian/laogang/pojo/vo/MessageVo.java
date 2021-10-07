package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.controller.contract.request.PolicyParam;
import com.lejian.laogang.repository.entity.MessageEntity;
import com.lejian.laogang.repository.entity.PolicyEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
public class MessageBo extends BaseBo{
    private Integer id;


    private String content;


    private Integer userId;



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

    public OldmanVo convertVo(){
        OldmanVo vo = new OldmanVo();
        BeanUtils.copyProperties(this,vo);
        vo.setAge(DateUtils.birthdayToAge(this.getBirthday()));
        vo.setMale(this.male.getDesc());
        return vo;
    }
}
