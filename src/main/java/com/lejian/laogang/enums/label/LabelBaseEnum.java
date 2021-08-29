package com.lejian.laogang.enums.label;


import com.google.common.collect.Maps;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.LabelBo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 标签枚举
 */
public interface LabelBaseEnum {

    /**
     * 标识
     *
     * @return
     */
    Integer getId();

    /**
     * 一级标签
     *
     * @return
     */
    Integer getParent();

    Integer getSort();

    List<LabelBo> label();


    /**
     * 根据目录 获取全部标签
     * @param parent
     * @return
     */
    static List<LabelBaseEnum> findByParent(Integer parent) {
        List<LabelBaseEnum> list = Lists.newArrayList();
        list.addAll(Arrays.stream(CustomLabel.values())
                .filter(item -> parent==null || item.getParent().intValue() == parent)
                .filter(item-> {
                    if (parent == null){
                        return item.getDisplay();
                    }
                    return true;
                })
                .collect(Collectors.toList()));
        list.addAll(Arrays.stream(EnumLabel.values())
                .filter(item -> parent==null ||item.getParent().intValue() == parent)
                .collect(Collectors.toList()));
        return list;
    }

    /**
     * 生成选中标签的 查询语句
     * @param labelIdList
     * @return
     */
    static JpaSpecBo generateJpaSpecBo(List<String> labelIdList) {
        JpaSpecBo jpaSpecBo = new JpaSpecBo();
        if (CollectionUtils.isEmpty(labelIdList)){
            return jpaSpecBo;
        }
        Map<String,List<String>> map = Maps.newHashMap();
        labelIdList.forEach(id->{
            if (id.contains("_")){
                String[] arr = id.split("_");
                if (!map.containsKey(arr[0])){
                    map.put(arr[0], Lists.newArrayList());
                }
                map.get(arr[0]).add(arr[1]);
            }else{
                map.put(id, Lists.newArrayList());
            }
        });
        for (CustomLabel customLabel : CustomLabel.values()){
            if (map.containsKey(String.valueOf(customLabel.getId()))){
                customLabel.setWhereCase(jpaSpecBo);
            }
        }
        for (EnumLabel enumLabel : EnumLabel.values()){
            if (map.containsKey(String.valueOf(enumLabel.getId()))){
                enumLabel.setWhereCase(jpaSpecBo,map.get(String.valueOf(enumLabel.getId())));
            }
        }
        return jpaSpecBo;
    }

    static List<String> getBaseLabelId(){
        return Lists.newArrayList("1","2","3","4","6","7","8","14","15");
    }

}
