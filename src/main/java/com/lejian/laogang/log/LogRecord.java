package com.lejian.laogang.log;

import java.lang.annotation.*;

/**
 * 执行后
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecord {
    String value() default "id";
    String table() default "";
    String valueType() default "oid";
}
