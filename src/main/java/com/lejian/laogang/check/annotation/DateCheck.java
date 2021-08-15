package com.lejian.laogang.check.annotation;


import com.lejian.laogang.check.checker.DateChecker;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@CheckAnno(DateChecker.class)
public @interface DateCheck {


    String value();
}