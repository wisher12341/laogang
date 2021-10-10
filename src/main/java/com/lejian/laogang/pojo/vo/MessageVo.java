package com.lejian.laogang.pojo.vo;

import com.lejian.laogang.pojo.bo.BaseBo;
import com.lejian.laogang.repository.entity.MessageEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class MessageVo{
    private Integer id;
    private String content;
    private Integer userId;
    private String sender;
    private String type;

}
