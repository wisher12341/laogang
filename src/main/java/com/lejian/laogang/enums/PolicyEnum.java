package com.lejian.laogang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 政策属性 枚举
 */
public interface PolicyEnum extends BusinessEnum{


    /**
     * 类型
     */
    @Getter
    @AllArgsConstructor
    enum Type implements PolicyEnum{
        A1(1,"小区职位"),
        A2(2,"养老补贴"),
        A3(3,"关爱服务"),
        A4(4,"走访帮困"),
        ;
        private Integer value;
        private String desc;
    }

}
