package com.lejian.laogang.check.annotation;


import com.lejian.laogang.check.checker.Checker;

import java.lang.annotation.*;

@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAnno {
    Class<? extends Checker> value();
}
