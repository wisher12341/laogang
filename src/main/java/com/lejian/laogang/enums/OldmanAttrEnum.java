package com.lejian.laogang.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * 老人属性 枚举
 */
public interface OldmanAttrEnum extends BusinessEnum {




    static Map<String, String> generateAttrWhere(String fieldName) {
        Map<String,String> map = Maps.newHashMap();
        Integer index = OldmanAttrMap.findIndex(fieldName);
        map.put("type",index+"");
        return map;
    }

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

    /**
     * 健康状态
     */
    @Getter
    @AllArgsConstructor
    enum Health implements OldmanAttrEnum {
        SN(1, "失能"),
        MB(2, "慢病"),
        ZLS(3, "肿瘤史"),;
        private Integer value;
        private String desc;

    }

    /**
     * 经济条件
     */
    @Getter
    @AllArgsConstructor
    enum Economic implements OldmanAttrEnum {
        DB(1, "低保"),
        YLBX(2, "养老保险"),;
        private Integer value;
        private String desc;

    }

    /**
     * 养老状态
     */
    @Getter
    @AllArgsConstructor
    enum ServiceStatus implements OldmanAttrEnum {
        JG(1, "机构养老"),
        SQ(2, "社区养老"),
        JJ(3, "居家养老"),
        ;
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
