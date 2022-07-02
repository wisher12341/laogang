package com.lejian.laogang.util;

import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 反射工具
 */
public class LjReflectionUtils {


    /**
     * 获取 对象的属性，并转换成Map  key:属性名
     * @param clazz
     * @return
     */
    public static Map<String,Field> getFieldToMap(Class clazz){
        if (clazz==null){
            return Maps.newHashMap();
        }
        Field[] fields=clazz.getDeclaredFields();
        return Arrays.stream(fields).collect(Collectors.toMap(Field::getName, Function.identity()));
    }

    public static Object getField(Object entity, String fieldName) {
        Field[] fields=entity.getClass().getDeclaredFields();
        for (Field field : fields){
            if (field.getName().equals(fieldName)){
                try {
                    field.setAccessible(true);
                    return field.get(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
