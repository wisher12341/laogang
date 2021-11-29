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
     * 人户情况
     */
    @Getter
    @AllArgsConstructor
    enum RH implements OldmanAttrEnum {
        A1(1,"人户一致"),
        A2(2,"人户分离"),
        A3(3,"查无此人"),
        ;
        private Integer value;
        private String desc;
    }



    /**
     * 性别
     */
    @Getter
    @AllArgsConstructor
    enum Male implements OldmanAttrEnum.labelEnum {
        MAN(1,"男",true),
        WOMAN(2,"女",true);
        private Integer value;
        private String desc;
        private Boolean display;
    }

    /**
     * 户籍
     */
    @Getter
    @AllArgsConstructor
    enum Huji implements OldmanAttrEnum.labelEnum {
        LOCAL(1,"本镇户籍",true),
        WOMAN(2,"非本镇户籍",true);
        private Integer value;
        private String desc;
        private Boolean display;
    }


    /**
     * 重点老人 枚举名 同数据库字段一致
     */
    @Getter
    @AllArgsConstructor
    enum IS_ZD implements OldmanAttrEnum.labelEnum{
        ZD(1,"重点老人",true),
        NOZD(2,"非重点老人",false)
        ;
        private Integer value;
        private String desc;
        private Boolean display;
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
    enum politics implements OldmanAttrEnum.labelEnum{
        A(1,"群众",true),
        B(2,"中共党员",true),
        C(3,"民主党派",true),
        ;
        private Integer value;
        private String desc;
        private Boolean display;
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
    enum eyesight implements OldmanAttrEnum.labelEnum{
        A(1,"失明",true),
        B(2,"弱视",true),
        C(3,"正常",true){
            @Override
            public String getName() {
                return "视力正常";
            }
        },
        ;
        private Integer value;
        private String desc;
        private Boolean display;
    }

    /**
     * 精神状态
     */
    @Getter
    @AllArgsConstructor
    enum psychosis implements  OldmanAttrEnum.labelEnum{
        A(1,"正常",true){
            @Override
            public String getName() {
                return "精神正常";
            }
        },
        B(2,"痴呆",true),
        C(3,"智障",true),
        ;
        private Integer value;
        private String desc;
        private Boolean display;
    }
}
