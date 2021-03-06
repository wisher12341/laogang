package com.lejian.laogang.check.bo;

import com.google.common.collect.Lists;
import com.lejian.laogang.check.annotation.EnumCheck;
import com.lejian.laogang.check.annotation.NullCheck;
import com.lejian.laogang.enums.ExcelEnum;
import com.lejian.laogang.enums.OldmanExcelEnum;
import com.lejian.laogang.util.LjReflectionUtils;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.lejian.laogang.common.ComponentRespCode.UN_KNOWN;


@Data
public class OldmanImportCheckBo extends AbstractCheckBo {

    @NullCheck
//    @LengthCheck(maxLength=10)
    private String name;

    //todo 正则验证
    @NullCheck
    private String idCard;

    @NullCheck
    private String address;

    @NullCheck
    private String areaCountry;

    @NullCheck
    private String areaTown;

    @NullCheck
    private String areaVillage;


    private String lng;
    private String lat;

    private String locationAddress;


    public static List<OldmanImportCheckBo> convert(Pair<List<String>, List<List<String>>> data) {
        List<OldmanImportCheckBo> list = Lists.newArrayList();
        try {
            IntStream.range(0, data.getSecond().size()).forEach(item -> {
                OldmanImportCheckBo checkBo = new OldmanImportCheckBo();
                checkBo.setNumCheck(item+1);
                list.add(checkBo);
            });
            Map<String, Field> fieldMap = LjReflectionUtils.getFieldToMap(OldmanImportCheckBo.class);
            for (int i = 0; i < data.getFirst().size(); i++) {
                ExcelEnum oldmanExcelEnum = ExcelEnum.findFieldName(data.getFirst().get(i), OldmanExcelEnum.class);
                Field field = fieldMap.get(oldmanExcelEnum.getFieldName());
                if(field==null){
                    continue;
                }
                field.setAccessible(true);
                for (int j = 0; j < list.size(); j++) {
                    field.set(list.get(j), data.getSecond().get(j).get(i));
                }
            }
        }catch (Exception e){
            UN_KNOWN.doThrowException("fail to convert",e);
        }
        return list;
    }
}
