package com.lejian.laogang.log;

import java.lang.annotation.*;

/**
 * 执行后
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecord {
}
