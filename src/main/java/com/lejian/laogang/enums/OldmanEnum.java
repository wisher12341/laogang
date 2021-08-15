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
     * 重点老人 枚举名 同数据库字段一致
     */
    @Getter
    @AllArgsConstructor
    enum IS_ZD implements OldmanEnum{
        ZD(1,"重点老人"),
        ;
        private Integer value;
        private String desc;
    }

    /**
     * 常驻地 房子性质
     */
    @Getter
    @AllArgsConstructor
    enum HOMEOWNER implements OldmanEnum{
        ZL(1,"租赁"),
        ZY(2,"自有"),
        ;
        private Integer value;
        private String desc;
    }

    /**
     * 房型  1有电梯楼房 2没电梯楼房 3独栋民宅 4多栋民宅-自己住 5多栋民宅-与他人共住
     */
    @Getter
    @AllArgsConstructor
    enum HOSE_TYPE implements OldmanEnum{
        A(1,"有电梯楼房"),
        B(2,"没电梯楼房"),
        C(3,"独栋民宅"),
        D(4,"多栋民宅-自己住"),
        E(5,"多栋民宅-与他人共住"),
        ;
        private Integer value;
        private String desc;
    }

    /**
     * 政治面貌
     */
    @Getter
    @AllArgsConstructor
    enum politics implements OldmanEnum{
        A(1,"群众"),
        B(2,"中共党员"),
        C(3,"民主党派"),
        ;
        private Integer value;
        private String desc;
    }

    /**
     * 学历
     */
    @Getter
    @AllArgsConstructor
    enum education implements OldmanEnum{
        A(1,"小学及以下"),
        B(2,"初中"),
        C(3,"高中/中专/技校"),
        D(4,"大学专科"),
        E(5,"大学本科"),
        F(6,"硕士研究生及以上"),
        G(7,"无")
        ;
        private Integer value;
        private String desc;
    }

    /**
     * 个人月收入
     */
    @Getter
    @AllArgsConstructor
    enum income implements OldmanEnum{
        A(1,"0-2499"),
        B(2,"2500-4999"),
        C(3,"5000-9999"),
        D(4,"10000元以上"),
        ;
        private Integer value;
        private String desc;
    }

    /**
     * 视力情况
     */
    @Getter
    @AllArgsConstructor
    enum eyesight implements OldmanEnum{
        A(1,"失明"),
        B(2,"弱视"),
        C(3,"正常"),
        ;
        private Integer value;
        private String desc;
    }

    /**
     * 精神状态
     */
    @Getter
    @AllArgsConstructor
    enum psychosis implements OldmanEnum{
        A(1,"正常"),
        B(2,"痴呆"),
        C(3,"智障"),
        ;
        private Integer value;
        private String desc;
    }
}
