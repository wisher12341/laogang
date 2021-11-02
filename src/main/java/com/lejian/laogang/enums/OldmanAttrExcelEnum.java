package com.lejian.laogang.enums;


import com.lejian.laogang.pojo.bo.OldmanAttrBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.lejian.laogang.common.Constant.IMPORT_RESET;

/**
 * 老人excel表 attr属性  表列名和 BO映射关系
 *
 * 额外内容  〖 包括
 * 多个内容 ┋分割
 */
@Getter
@AllArgsConstructor
public enum OldmanAttrExcelEnum implements ExcelEnum{
    ID_CARD("2",null,null),
    a33("33",OldmanAttrEnum.OldmanAttrType.A6.getValue().toString(),OldmanAttrEnum.WorkStatus.class),
    a34("34",OldmanAttrEnum.OldmanAttrType.A1.getValue().toString(),OldmanAttrEnum.CommunityWork.class),
    a36("36",OldmanAttrEnum.OldmanAttrType.A2.getValue().toString(),OldmanAttrEnum.FamilyType.class),
    a37("37",OldmanAttrEnum.OldmanAttrType.A3.getValue().toString(),OldmanAttrEnum.Faimly.class),
    a38("38",OldmanAttrEnum.OldmanAttrType.A4.getValue().toString(),OldmanAttrEnum.Income.class),
    a39("39",OldmanAttrEnum.OldmanAttrType.A5.getValue().toString(),OldmanAttrEnum.YB.class),
    a51("51",OldmanAttrEnum.OldmanAttrType.A7.getValue().toString(),null){
        @Override
        public void handle(Object bo, Object value) {
            OldmanAttrBo attrBo = (OldmanAttrBo) bo;
            attrBo.setType(OldmanAttrEnum.OldmanAttrType.A7);
            attrBo.setValue(OldmanAttrEnum.EX.A);
        }
    },
    a52("52",OldmanAttrEnum.OldmanAttrType.A7.getValue().toString(),null){
        @Override
        public void handle(Object bo, Object value) {
            OldmanAttrBo attrBo = (OldmanAttrBo) bo;
            attrBo.setType(OldmanAttrEnum.OldmanAttrType.A7);
            attrBo.setValue(OldmanAttrEnum.EX.B);
        }
    },
    a53("53",OldmanAttrEnum.OldmanAttrType.A8.getValue().toString(),OldmanAttrEnum.SN.class),
    a54("54",OldmanAttrEnum.OldmanAttrType.A9.getValue().toString(),OldmanAttrEnum.YWGM.class),
    a55("55",OldmanAttrEnum.OldmanAttrType.A10.getValue().toString(),OldmanAttrEnum.MB.class),
    a56("56",OldmanAttrEnum.OldmanAttrType.A11.getValue().toString(),OldmanAttrEnum.ZL.class),
    a57("57",OldmanAttrEnum.OldmanAttrType.A12.getValue().toString(),OldmanAttrEnum.BQJWS.class),
    a58("58",OldmanAttrEnum.OldmanAttrType.A13.getValue().toString(),OldmanAttrEnum.ServiceStatus.class),
    a59("59",OldmanAttrEnum.OldmanAttrType.A15.getValue().toString(),OldmanAttrEnum.SQYL.class),
    a60("60",OldmanAttrEnum.OldmanAttrType.A14.getValue().toString(),OldmanAttrEnum.JJYLXM.class),
    a61("61",OldmanAttrEnum.OldmanAttrType.A16.getValue().toString(),OldmanAttrEnum.ZCD.class),
    a62("62",OldmanAttrEnum.OldmanAttrType.A16.getValue().toString(),OldmanAttrEnum.ZCD.class),
    a63("63",OldmanAttrEnum.OldmanAttrType.A16.getValue().toString(),OldmanAttrEnum.ZCD.class),
    ;

    private String columnName;
    /**
     * 类型 type的值
     */
    private String fieldName;
    private Class<? extends BusinessEnum> enumType;

}
