package com.lejian.laogang.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 方法注解
@Retention(RetentionPolicy.RUNTIME) // 运行时可见
public @interface CodeField {
}
