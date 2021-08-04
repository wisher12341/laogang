package com.lejian.laogang.repository;

import lombok.Getter;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 归类where子句的各式条件
 * key: 字段名(entity的字段名 不是数据库的)  value:字段值
 */
@Getter
public class WhereBo {
    /**
     * 等于
     */
    Map<String,Object> equalMap = new HashMap<>();
    /**
     * 不等于
     */
    Map<String,Object> notEqualMap = new HashMap<>();
    /**
     * like  使用时 value 要带 %%
     */
    Map<String,Object> likeMap = new HashMap<>();
    /**
     * in
     */
    Map<String,List<Object>> inMap = new HashMap<>();
    /**
     * 小于等于
     */
    Map<String,Object> lessEMap = new HashMap<>();
    /**
     * 大于等于
     */
    Map<String,Object> greatEMap = new HashMap<>();
    /**
     * 大于
     */
    Map<String,Object> greatMap = new HashMap<>();
    /**
     * or (!=)
     * and ( or or or )
     */
    Map<String,Object> orNotEquipMap = new HashMap<>();

    public String getSql() {
        StringBuilder whereCase = new StringBuilder();
        if (MapUtils.isNotEmpty(equalMap)) {
            Iterator iterator = equalMap.keySet().iterator();
            String key = (String) iterator.next();
            whereCase.append(key).append("='").append(equalMap.get(key)).append("'");
            while (iterator.hasNext()) {
                whereCase.append(" and ");
                key = (String) iterator.next();
                whereCase.append(key).append("='").append(equalMap.get(key)).append("'");
            }
        }
        return whereCase.toString();
    }
}
