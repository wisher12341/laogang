package com.lejian.laogang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息属性 枚举
 */
public interface MessageEnum extends BusinessEnum{


    /**
     * 类型
     */
    @Getter
    @AllArgsConstructor
    enum Type implements MessageEnum {
        A1(1,"政策信息"),
        ;
        private Integer value;
        private String desc;
    }

}
