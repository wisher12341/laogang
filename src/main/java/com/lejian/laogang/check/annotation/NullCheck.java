package com.lejian.laogang.check.annotation;


import com.lejian.laogang.check.checker.NullChecker;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@CheckAnno(NullChecker.class)
public @interface NullCheck {

}
