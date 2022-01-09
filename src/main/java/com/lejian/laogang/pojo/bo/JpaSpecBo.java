package com.lejian.laogang.pojo.bo;

import com.google.common.collect.Lists;
import com.lejian.laogang.util.StringUtils;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用于JPA 复杂查询， 归类where子句的各式条件
 * key: 字段名(entity的字段名 不是数据库的)  value:字段值
 */
@Getter
public class JpaSpecBo {
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

    /**
     * or (like)
     * and ( or or or )
     */
    Map<String,Object> orLikeMap = new HashMap<>();

    List<String> viewLabelLike = Lists.newArrayList();

    public String getSql() {
        return getSql("");
    }

    public String getSql(String prex) {
        StringBuilder whereCase = new StringBuilder();
        if (MapUtils.isNotEmpty(equalMap)) {
            Iterator iterator = equalMap.keySet().iterator();
            String key = (String) iterator.next();
            String convertKey =  StringUtils.camelToUnderline(key);
            whereCase.append(prex+convertKey).append("='").append(equalMap.get(key)).append("'");
            while (iterator.hasNext()) {
                whereCase.append(" and ");
                key = (String) iterator.next();
                convertKey = StringUtils.camelToUnderline(key);
                whereCase.append(prex+convertKey).append("='").append(equalMap.get(key)).append("'");
            }
        }
        if (MapUtils.isNotEmpty(notEqualMap)) {
            Iterator iterator = notEqualMap.keySet().iterator();
            String key = (String) iterator.next();
            String convertKey =  StringUtils.camelToUnderline(key);
            if (whereCase.length()>0){
                whereCase.append(" and ");
            }
            whereCase.append(prex+convertKey).append("!='").append(notEqualMap.get(key)).append("'");
            while (iterator.hasNext()) {
                whereCase.append(" and ");
                key = (String) iterator.next();
                convertKey = StringUtils.camelToUnderline(key);
                whereCase.append(prex+convertKey).append("!='").append(notEqualMap.get(key)).append("'");
            }
        }
        if (MapUtils.isNotEmpty(lessEMap)) {
            Iterator iterator = lessEMap.keySet().iterator();
            String key = (String) iterator.next();
            String convertKey =  StringUtils.camelToUnderline(key);
            if (whereCase.length()>0){
                whereCase.append(" and ");
            }
            whereCase.append(prex+convertKey).append("<='").append(lessEMap.get(key)).append("'");
            while (iterator.hasNext()) {
                whereCase.append(" and ");
                key = (String) iterator.next();
                convertKey = StringUtils.camelToUnderline(key);
                whereCase.append(prex+convertKey).append("<='").append(lessEMap.get(key)).append("'");
            }
        }
        if (MapUtils.isNotEmpty(greatEMap)) {
            Iterator iterator = greatEMap.keySet().iterator();
            String key = (String) iterator.next();
            String convertKey =  StringUtils.camelToUnderline(key);
            if (whereCase.length()>0){
                whereCase.append(" and ");
            }
            whereCase.append(prex+convertKey).append(">='").append(greatEMap.get(key)).append("'");
            while (iterator.hasNext()) {
                whereCase.append(" and ");
                key = (String) iterator.next();
                convertKey = StringUtils.camelToUnderline(key);
                whereCase.append(prex+convertKey).append(">='").append(greatEMap.get(key)).append("'");
            }
        }
        if (MapUtils.isNotEmpty(greatMap)) {
            Iterator iterator = greatMap.keySet().iterator();
            String key = (String) iterator.next();
            String convertKey =  StringUtils.camelToUnderline(key);
            if (whereCase.length()>0){
                whereCase.append(" and ");
            }
            whereCase.append(prex+convertKey).append(">'").append(greatMap.get(key)).append("'");
            while (iterator.hasNext()) {
                whereCase.append(" and ");
                key = (String) iterator.next();
                convertKey = StringUtils.camelToUnderline(key);
                whereCase.append(prex+convertKey).append(">'").append(greatMap.get(key)).append("'");
            }
        }
        if (MapUtils.isNotEmpty(likeMap)) {
            Iterator iterator = likeMap.keySet().iterator();
            String key = (String) iterator.next();
            String convertKey =  StringUtils.camelToUnderline(key);
            if (whereCase.length()>0){
                whereCase.append(" and ");
            }
            whereCase.append(prex+convertKey).append(" like '%").append(likeMap.get(key)).append("%'");
            while (iterator.hasNext()) {
                whereCase.append(" and ");
                key = (String) iterator.next();
                convertKey = StringUtils.camelToUnderline(key);
                whereCase.append(prex+convertKey).append(" like '%").append(likeMap.get(key)).append("%'");
            }
        }
        if (MapUtils.isNotEmpty(inMap)) {
            if (whereCase.length()>0){
                whereCase.append(" and ");
            }
            Iterator iterator = inMap.keySet().iterator();
            String key = (String) iterator.next();
            String convertKey =  StringUtils.camelToUnderline(key);
            whereCase.append(prex+convertKey).append(" in (").append(inMap.get(key).stream().map(item -> "'" + item + "'").collect(Collectors.joining(","))).append(")");
            while (iterator.hasNext()) {
                whereCase.append(" and ");
                key = (String) iterator.next();
                convertKey = StringUtils.camelToUnderline(key);
                whereCase.append(prex+convertKey).append(" in (").append(inMap.get(key).stream().map(item -> "'" + item + "'").collect(Collectors.joining(","))).append(")");
            }
        }
        if(CollectionUtils.isNotEmpty(viewLabelLike)){
            Iterator iterator = viewLabelLike.iterator();
            if (whereCase.length()>0){
                whereCase.append(" and ");
            }
            whereCase.append(prex+"label").append(" like '").append(iterator.next()).append("'");
            while (iterator.hasNext()) {
                whereCase.append(" and ");
                whereCase.append(prex+"label").append(" like '").append(iterator.next()).append("'");
            }
        }
        return whereCase.toString();
    }

}
