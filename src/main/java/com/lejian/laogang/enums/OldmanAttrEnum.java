package com.lejian.laogang.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * 老人属性 枚举
 */
public interface OldmanAttrEnum extends BusinessEnum {

    interface labelEnum extends OldmanAttrEnum{
        /**
         * 是否在一开始展示
         * @return
         */
        Boolean getDisplay();

        /**
         * 展示名称
         * @return
         */
        default String getName(){
            return null;
        };
    }

    /**
     * 医保信息
     */
    @Getter
    @AllArgsConstructor
    enum OldmanAttrType implements OldmanAttrEnum {
        A1(1, "从事社区工作",CommunityWork.class),
        A2(2, "家庭结构",FamilyType.class),
        A3(3, "家庭属性",Faimly.class),
        A4(4,"生活来源",Income.class),
        A5(5,"医保",YB.class),
        A6(6,"工作状态",WorkStatus.class),
        A7(7,"恶习",EX.class),
        A8(8,"失能情况",SN.class),
        A9(9,"药物过敏",YWGM.class),
        A10(10,"慢病",MB.class),
        A11(11,"肿瘤史",ZL.class),
        A12(12,"病情既往史",BQJWS.class),
        A13(13,"养老状态",ServiceStatus.class),
        A14(14,"居家养老项目",JJYLXM.class),
        A15(15,"社区养老扩展信息",SQYL.class),
        A16(16,"助餐点",ZCD.class),
        ;
        private Integer value;
        private String desc;
        private Class enumClass;

        public static Class findEnumClass(Integer type){
            for (OldmanAttrType item:OldmanAttrType.values()){
                if (item.getValue()==type.intValue()){
                    return item.getEnumClass();
                }
            }
            return null;
        }

    }


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
    enum FamilyType implements labelEnum {
        CHUNLAO(1, "纯老",true),
        DUJU(2, "独居",true),
        SHUDU(3, "失独",true),
        GULAO(4, "孤老",true),
        YLYYL(5, "一老养一老",true),
        SZRY(6, "三支人员",true),
        OTHER(0, "其他",false){
            @Override
            public String getName() {
                return "家庭结构-其他";
            }
        },
        A(7,"三峽移民",true);
        private Integer value;
        private String desc;
        private Boolean display;

    }


    /**
     * 养老状态
     */
    @Getter
    @AllArgsConstructor
    enum ServiceStatus implements labelEnum {
        JG(1, "机构养老",true),
        SQ(2, "社区养老",true),
        JJ(3, "居家养老",true),
        ;
        private Integer value;
        private String desc;
        private Boolean display;

    }

    /**
     * 工作状态
     */
    @Getter
    @AllArgsConstructor
    enum WorkStatus implements OldmanAttrEnum {
        B(2, "退休"),
        C(3, "在职"),
        D(4,"返聘"),
        E(5,"离休")
        ;
        private Integer value;
        private String desc;

    }

    /**
     * 社区工作
     */
    @Getter
    @AllArgsConstructor
    enum CommunityWork implements OldmanAttrEnum {
        B(2, "楼组长"),
        C(3, "业务会成员"),
        D(4,"村/居委"),
        E(5,"队/组"),
        F(6,"睦邻点负责人"),
        G(7,"睦邻点指导员"),
        H(8,"志愿者"),
        I(9,"其他"),
        ;
        private Integer value;
        private String desc;

    }



    /**
     * 家庭属性
     */
    @Getter
    @AllArgsConstructor
    enum Faimly implements labelEnum {
        A(1, "独生子女",true),
        B(2, "退伍军人",true),
        C(3, "烈士家属",true),
        D(4,"军属",true),
        E(5,"侨属",true),
        F(6,"其他",false){
            @Override
            public String getName() {
                return "家庭属性-其他";
            }
        }
        ;
        private Integer value;
        private String desc;
        private Boolean display;

    }


    /**
     * 生活来源
     */
    @Getter
    @AllArgsConstructor
    enum Income implements labelEnum {
        A(1, "子女赡养",true),
        B(2, "低收入",true),
        C(3, "低保",true),
        D(4,"帮困",true),
        E(5,"退休金",true),
        F(6,"其他",false)
        ;
        private Integer value;
        private String desc;
        private Boolean display;

    }

    /**
     * 医保信息
     */
    @Getter
    @AllArgsConstructor
    enum YB implements OldmanAttrEnum {
        A(1, "城镇医保"),
        B(2, "农保"),
        C(3, "非上海医保"),
        ;
        private Integer value;
        private String desc;

    }


    /**
     * 恶习
     */
    @Getter
    @AllArgsConstructor
    enum EX implements OldmanAttrEnum {
        A(1, "喝酒"),
        B(2, "抽烟"),
        ;
        private Integer value;
        private String desc;

    }


    /**
     * 失能情况
     */
    @Getter
    @AllArgsConstructor
    enum SN implements OldmanAttrEnum {
        A(1, "上厕所"),
        B(2, "洗澡"),
        C(3, "穿衣"),
        D(4,"上下床"),
        E(5,"室内行走"),
        F(6,"吃饭"),
        G(7,"不需要协助")
        ;
        private Integer value;
        private String desc;

    }


    /**
     * 药物过敏
     */
    @Getter
    @AllArgsConstructor
    enum YWGM implements OldmanAttrEnum {
        A(1, "青霉素过敏"),
        ;
        private Integer value;
        private String desc;

    }

    /**
     * 慢病
     */
    @Getter
    @AllArgsConstructor
    enum MB implements OldmanAttrEnum {
        A(1, "慢阻肺"),
        B(2, "高血压"),
        C(3, "糖尿病"),
        D(4,"冠心病"),
        E(5,"癫病"),
        F(6,"帕金森"),
        G(7,"脑卒中(脑出血、脑梗塞)"),
        H(8,"甲状腺功能不全"),
        I(9,"慢性肾功能不全"),
        O(10,"慢性肝功能不全"),
        K(11,"尿毒症"),
        L(12,"肝硬化"),
        M(13,"心功能不全"),
        N(14,"消化道疾病"),
        P(15,"阿兹海默症"),
        Q(16,"免疫系统疾病(类风湿性关节炎、红斑狼疮)"),
        ;
        private Integer value;
        private String desc;

    }

    /**
     * 肿瘤史
     */
    @Getter
    @AllArgsConstructor
    enum ZL implements OldmanAttrEnum {
        A(1, "良性肿瘤"),
        B(2, "恶性肿瘤"),
        ;
        private Integer value;
        private String desc;

    }

    /**
     * 病情既往史
     */
    @Getter
    @AllArgsConstructor
    enum BQJWS implements OldmanAttrEnum {
        A(1, "骨折"),
        B(2, "中风"),
        C(3, "偏瘫"),
        D(4,"支架"),
        E(5,"起搏器"),
        F(6,"搭桥"),
        G(7,"其他"),
        H(8,"做过何种手术"),
        ;
        private Integer value;
        private String desc;

    }

    /**
     * 居家养老项目
     */
    @Getter
    @AllArgsConstructor
    enum JJYLXM implements OldmanAttrEnum {
        A(1, "长护险"),
        B(2, "居家养老(民政局)"),
        C(3, "居家养老(镇级)"),
        D(4,"其他"),
        ;
        private Integer value;
        private String desc;

    }

    /**
     * 社区养老扩展信息
     */
    @Getter
    @AllArgsConstructor
    enum SQYL implements OldmanAttrEnum {
        A(1, "长者照顾之家"),
        B(2, "日间照料中心"),
        C(3, "助餐"),
        ;
        private Integer value;
        private String desc;

    }
    /**
     * 助餐点
     */
    @Getter
    @AllArgsConstructor
    enum ZCD implements OldmanAttrEnum {
        A(1, "现在有助餐"),
        B(2, "将来不需要助餐"),
        C(3, "固定点助餐"),
        D(4, "需要送餐到家"),
        ;
        private Integer value;
        private String desc;

    }
   ;


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
