package com.lejian.laogang.util;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public final class LaogangContext {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

    public static <T> T get(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        return (T) map.get(key);
    }

    public static <T> void set(String key, T value) {
        Map<String, Object> map = THREAD_LOCAL.get();
        map.put(key, value);
    }

    public static boolean contain(String key){
        Map<String, Object> map = THREAD_LOCAL.get();
        return map.containsKey(key);
    }
}
