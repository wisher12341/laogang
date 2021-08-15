package com.lejian.laogang.check.checker;

import com.lejian.laogang.check.annotation.EnumCheck;
import com.lejian.laogang.check.bo.CheckFieldBo;
import com.lejian.laogang.enums.BusinessEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

import static com.lejian.laogang.common.ComponentRespCode.ENUM_CHECK;
import static com.lejian.laogang.common.Constant.IMPORT_RESET;

@Component
public class EnumChecker implements Checker {

    @Override
    public CheckFieldBo check(String name, Object value, Annotation annotation) {
        EnumCheck enumCheck= (EnumCheck) annotation;
        if(value==null || StringUtils.isBlank(String.valueOf(value)) || String.valueOf(value).equals(IMPORT_RESET)){
            return null;
        }
        Class<? extends BusinessEnum> enumClass=(enumCheck).value();
        for(BusinessEnum businessEnum: enumClass.getEnumConstants()){
            if(businessEnum.getDesc().equals(String.valueOf(value))){
                return null;
            }
        }

        return new CheckFieldBo(name,ENUM_CHECK.getDisplayMessage());
    }
}
