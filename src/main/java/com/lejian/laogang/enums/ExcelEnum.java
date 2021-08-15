package com.lejian.laogang.enums;


import com.lejian.laogang.pojo.bo.BaseBo;

import static com.lejian.laogang.common.ComponentRespCode.UN_KNOW_COLUMN;

public interface ExcelEnum {

    String getColumnName();
    String getFieldName();
    Class getEnumType();

    static ExcelEnum findFieldName(String columnName, Class<? extends ExcelEnum> enumClass){
        columnName = columnName.split("、")[0].trim();
        for(ExcelEnum excelEnum: enumClass.getEnumConstants()){
            if(excelEnum.getColumnName().equals(columnName)){
                return excelEnum;
            }
        }
        return null;
    }

    static ExcelEnum findColumnName(String filedName, Class<? extends ExcelEnum> enumClass){
        for(ExcelEnum excelEnum: enumClass.getEnumConstants()){
            if(excelEnum.getFieldName().equals(filedName)){
                return excelEnum;
            }
        }
        UN_KNOW_COLUMN.doThrowException();
        return null;
    }

    /**
     * 特殊处理
     * @return
     */
    default void handle(Object bo,Object value){ }
}
