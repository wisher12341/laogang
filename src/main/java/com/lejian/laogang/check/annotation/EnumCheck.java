package com.lejian.laogang.check.annotation;


import com.lejian.laogang.check.checker.EnumChecker;
import com.lejian.laogang.enums.BusinessEnum;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@CheckAnno(EnumChecker.class)
public @interface EnumCheck {


    Class<? extends BusinessEnum> value();
}
