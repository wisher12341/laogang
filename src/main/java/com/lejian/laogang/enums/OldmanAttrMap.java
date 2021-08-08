package com.lejian.laogang.enums;

import com.google.common.collect.Maps;

import java.util.Map;

public class OldmanAttrMap {

    private static final Map<String,Integer> INDEX_MAP = Maps.newHashMap();


    static {
        INDEX_MAP.put("FamilyType",2);
        INDEX_MAP.put("ServiceStatus",13);
        INDEX_MAP.put("Health",18);
        INDEX_MAP.put("Economic",19);

    }

    public static Integer findIndex(String name){
        return INDEX_MAP.get(name);
    }
}
