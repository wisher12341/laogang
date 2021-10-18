package com.lejian.laogang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号 枚举
 */
public interface UserEnum extends BusinessEnum{

    /**
     * 服务人员类型
     */
    @Getter
    @AllArgsConstructor
    enum Role implements UserEnum {
        A1(1,"管理员"),
        A2(2,"村/居委"),
        A3(3,"社区事务受理中心"),
        A4(4,"社建办"),
        A5(5,"卫生院"),
        ;
        private Integer value;
        private String desc;
    }
}
