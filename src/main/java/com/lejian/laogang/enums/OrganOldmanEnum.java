package com.lejian.laogang.enums;

import com.google.common.collect.Lists;
import com.lejian.laogang.pojo.vo.MenuVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


public interface OrganOldmanEnum extends BusinessEnum{


    /**
     * 服务人员类型
     */
    @Getter
    @AllArgsConstructor
    enum Body implements OrganOldmanEnum {
        A1(1,"正常"),
        A2(2,"失能") ,
        A3(3,"半失能"),
        A4(4,"失智"),

        ;
        private Integer value;
        private String desc;

    }
}
