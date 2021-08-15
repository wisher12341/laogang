package com.lejian.laogang.check.checker;


import com.lejian.laogang.check.bo.CheckFieldBo;

import java.lang.annotation.Annotation;

public interface Checker {

    CheckFieldBo check(String name, Object value, Annotation annotation);
}
