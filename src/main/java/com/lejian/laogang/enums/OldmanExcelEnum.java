package com.lejian.laogang.enums;


import com.lejian.laogang.pojo.bo.OldmanBo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import static com.lejian.laogang.common.Constant.IMPORT_RESET;

/**
 * 老人excel表  表列名和 BO映射关系
 */
@Getter
@AllArgsConstructor
public enum OldmanExcelEnum implements ExcelEnum{

    NAME("1","name",null),
    ID_CARD("2","idCard",null),
    MALE("3","male",OldmanEnum.Male.class),
    GJ("4","country",null),
    TB("5",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            if (String.valueOf(value).equals("是")){
                oldmanBo.setCountry("中国台湾");
            }

        }
    },
    HJ("6","huji",OldmanEnum.Huji.class),
    residence("7","residence",null),
    /**
     * 同序号多个的 话  后者覆盖前者
     */
    hujiAddress("9","hujiAddress",null),
    homeowner("10","homeowner",OldmanEnum.HOMEOWNER.class),
    house_type("11",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            if (String.valueOf(value).equals("民宅")){
                oldmanBo.setHouseType(OldmanEnum.HOSE_TYPE.C);
            }else {
                oldmanBo.setHouseType(OldmanEnum.HOSE_TYPE.B);
            }

        }
    },
    a12("12","null",null){
        @Override
        public void handle(Object bo,Object value) {
            //不填 默认 否
            OldmanBo oldmanBo = (OldmanBo) bo;
            if (String.valueOf(value).equals("是")){
                oldmanBo.setHouseType(OldmanEnum.HOSE_TYPE.A);
            }

        }
    },
    floor("13","floor",null),
    a15("15","null",null){
        @Override
        public void handle(Object bo,Object value) {
            //不填 默认 否
            OldmanBo oldmanBo = (OldmanBo) bo;
            if (String.valueOf(value).equals("与其他人共住一栋房")){
                oldmanBo.setHouseType(OldmanEnum.HOSE_TYPE.E);
            }else if(String.valueOf(value).equals("一个人住一栋房")){
                oldmanBo.setHouseType(OldmanEnum.HOSE_TYPE.D);
            }
        }
    },
    politics("16","politics",OldmanEnum.politics.class),
    landline_number("17","landlineNumber",null),
    phone("18","phone",null),
    education("19","education",OldmanEnum.education.class),
    a21("21",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
               oldmanBo.setAreaVillage(str);
            }
        }
    },
    a22("22",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaCustomOne(str);
            }
        }
    },
    a23("23",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaCustomOne(str);
            }
        }
    },
    a24("24",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaCustomOne(str);
            }
        }
    },
    a25("25",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaCustomOne(str);
            }
        }
    },
    a26("26",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaCustomOne(str);
            }
        }
    },
    a27("27",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaCustomOne(str);
            }
        }
    },
    a28("28",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaCustomOne(str);
            }
        }
    },
    a29("29",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaVillage(str);
            }
        }
    },
    a30("30",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaCustomOne(str);
            }
        }
    },
    a31("31",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaCustomOne(str);
            }
        }
    },
    a32("32",null,null){
        @Override
        public void handle(Object bo,Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = String.valueOf(value);
            if (!IMPORT_RESET.contains(str)){
                oldmanBo.setAreaCustomOne(str);
            }
        }
    },
    a46("46","war",null),
    a47("47","war",null),
    income("35","income",OldmanEnum.income.class),
    vaccine("41","vaccine",null),
    vaccineFirst("42","vaccineFirst",null),
    vaccineSec("43","vaccineSec",null),
    commercialInsurance("44","commercialInsurance",null),
    commercialInsuranceJt("45","commercialInsurance",null),
    bloodiness("48","bloodiness",null),
    eyesight("49","eyesight",OldmanEnum.eyesight.class),
    psychosis("50","psychosis",OldmanEnum.psychosis.class),


    GPS("90",null,null){
        @Override
        public void handle(Object bo, Object value) {
            OldmanBo oldmanBo = (OldmanBo) bo;
            String str = (String) value;
            if (StringUtils.isBlank(str)){
                return;
            }
            String[] gps;
            if (str.contains(",")) {
                //英文
                gps = String.valueOf(value).split("\\[")[1].split(",");
            }else {
                //中文
                gps = String.valueOf(value).split("\\[")[1].split("，");
            }
            String desc = String.valueOf(value).split("\\[")[0];
            if (desc.equals("上海市浦东新区")){
                return;
            }
            oldmanBo.setLng(gps[0]);
            oldmanBo.setLat(gps[1].replaceAll("]",""));
            oldmanBo.setGpsDesc(desc);
        }
    },
    ;

    private String columnName;
    private String fieldName;
    private Class<? extends BusinessEnum> enumType;
}
