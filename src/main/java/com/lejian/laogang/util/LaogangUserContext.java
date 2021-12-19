package com.lejian.laogang.util;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class LaogangUserContext {

    /**
     * key: username
     */
    private static Cache<String,Map<String,Object>> cache = Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    public static <T> T get(String key) {
        Map<String, Object> map = cache.asMap().get(UserUtils.getUsername());
        if (MapUtils.isEmpty(map)){
            return null;
        }
        return (T) map.get(key);
    }

    public static <T> void set(String key, T value) {
        Map<String, Object> map = cache.asMap().computeIfAbsent(UserUtils.getUsername(),k-> Maps.newHashMap());
        map.put(key, value);
    }

}
