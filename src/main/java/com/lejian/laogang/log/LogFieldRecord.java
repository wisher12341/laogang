package com.lejian.laogang.log;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogFieldRecord {
    String value();
    //db 默认值， 枚举后的值，防止被第二次 记录成 更新
    String defaultValue() default "";
}
