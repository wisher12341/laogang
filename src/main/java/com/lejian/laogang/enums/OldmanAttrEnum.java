package com.lejian.laogang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 老人属性 枚举
 */
public interface OldmanAttrEnum extends BusinessEnum {


    /**
     * 家庭结构
     */
    @Getter
    @AllArgsConstructor
    enum FamilyType implements OldmanAttrEnum {
        CHUNLAO(1, "纯老"),
        DUJU(2, "独居"),
        SHUDU(3, "失独"),
        GULAO(4, "孤老"),
        YLYYL(5, "一老养一老"),
        SZRY(6, "三支人员"),
        OTHER(0, "其他"),;
        private Integer value;
        private String desc;

    }

    static OldmanAttrEnum findByTypeAndValue(Integer type, Integer value) {
        OldmanAttrEnum[] enums = findEnum(type);
        for (OldmanAttrEnum oldmanAttrEnum : enums){
            if (oldmanAttrEnum.getValue().intValue() == value){
                return oldmanAttrEnum;
            }
        }
        return null;
    }

    static OldmanAttrEnum[] findEnum(Integer type) {
        switch (type) {
            case 2:
                return FamilyType.values();
            default:
                return null;
        }
    }
}
