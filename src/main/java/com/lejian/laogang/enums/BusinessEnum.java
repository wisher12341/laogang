package com.lejian.laogang.enums;

public interface BusinessEnum {

    /**
     * 获取 枚举 值（DB存的数字）
     * @return
     */
    Integer getValue();
    /**
     * 枚举描述
     * @return
     */
    String getDesc();


    /**
     * 默认值
     */
    enum DefaultValue implements BusinessEnum{
        NULL;

        @Override
        public Integer getValue() {
            return null;
        }

        @Override
        public String getDesc() {
            return null;
        }
    }



    static BusinessEnum find(Integer value, Class<? extends BusinessEnum> enumClass){
        if (value==null){
            return BusinessEnum.DefaultValue.NULL;
        }
        for(BusinessEnum businessEnum: enumClass.getEnumConstants()){
            if(businessEnum.getValue().intValue()==value){
                return businessEnum;
            }
        }
        return BusinessEnum.DefaultValue.NULL;
    }

    static BusinessEnum find(String desc, Class<? extends BusinessEnum> enumClass){
        for(BusinessEnum businessEnum: enumClass.getEnumConstants()){
            if(businessEnum.getDesc().equals(desc)){
                return businessEnum;
            }
        }
        return BusinessEnum.DefaultValue.NULL;
    }

    static BusinessEnum find(Integer value, String className) {
        switch (className){
            case "male":
                return find(value,OldmanEnum.Male.class);
            case "huji":
                return find(value,OldmanEnum.Huji.class);
            case "FamilyType":
            case  "2":
                return find(value,OldmanAttrEnum.FamilyType.class);
            case "Income":
            case  "4":
                return find(value,OldmanAttrEnum.Income.class);
            case "ServiceStatus":
                return find(value,OldmanAttrEnum.ServiceStatus.class);
            case "999":
                return find(value,OldmanAttrEnum.OldmanAttrType.class);
        }
        return null;
    }
}
