package com.lejian.laogang.check.checker;


import com.lejian.laogang.check.annotation.DateCheck;
import com.lejian.laogang.check.bo.CheckFieldBo;
import com.lejian.laogang.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

import static com.lejian.laogang.common.ComponentRespCode.DATE_CHECK;


@Component
public class DateChecker implements Checker {

    @Override
    public CheckFieldBo check(String name, Object value, Annotation annotation) {
        DateCheck enumCheck= (DateCheck) annotation;
        if(value==null || StringUtils.isBlank(String.valueOf(value))){
            return null;
        }
        String format=enumCheck.value();
        if(!DateUtils.isCorrect(format,String.valueOf(value))){
            return new CheckFieldBo(name,DATE_CHECK.getDisplayMessage());
        }
        return null;

    }
}
