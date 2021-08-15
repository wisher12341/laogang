package com.lejian.laogang.check.annotation;


import com.lejian.laogang.check.checker.EnumChecker;

import java.lang.annotation.*;

/**
 * 正则校验
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@CheckAnno(EnumChecker.class)
public @interface RegularCheck {
    int maxLength();
}
