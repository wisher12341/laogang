package com.lejian.laogang.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 老人属性 枚举
 */
public interface OldmanEnum extends BusinessEnum{


    /**
     * 性别
     */
    @Getter
    @AllArgsConstructor
    enum Male implements OldmanEnum{
        MAN(1,"男"),
        WOMAN(2,"女");
        private Integer value;
        private String desc;
    }

    /**
     * 户籍
     */
    @Getter
    @AllArgsConstructor
    enum Huji implements OldmanEnum{
        LOCAL(1,"本镇户籍"),
        WOMAN(2,"非本镇户籍");
        private Integer value;
        private String desc;
    }

    /**
     * 家庭结构
     */
    @Getter
    @AllArgsConstructor
    enum FamilyType implements OldmanEnum{
        CHUNLAO(1,"纯老"),
        DUJU(2,"独居"),
        SHUDU(3,"失独"),
        GULAO(4,"孤老"),
        YLYYL(5,"一老养一老"),
        SZRY(6,"三支人员"),
        OTHER(0,"其他"),
        ;
        private Integer value;
        private String desc;
    }

}
