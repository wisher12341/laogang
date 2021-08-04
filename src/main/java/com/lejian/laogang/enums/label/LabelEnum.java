package com.lejian.laogang.enums.label;


import com.google.common.collect.Maps;
import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.pojo.bo.LabelBo;
import com.lejian.laogang.repository.WhereBo;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
public interface LabelEnum {

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
    static List<LabelEnum> findByParent(Integer parent) {
        List<LabelEnum> list = Lists.newArrayList();
        list.addAll(Arrays.stream(CustomLabel.values())
                .filter(item -> parent==null || item.getParent().intValue() == parent)
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
    static WhereBo generateWhere(List<String> labelIdList) {
        WhereBo whereBo = new WhereBo();
        if (CollectionUtils.isEmpty(labelIdList)){
            return whereBo;
        }
        Map<String,String> map = Maps.newHashMap();
        labelIdList.forEach(id->{
            if (id.contains("_")){
                String[] arr = id.split("_");
                map.put(arr[0],arr[1]);
            }else{
                map.put(id, StringUtils.EMPTY);
            }
        });
        for (CustomLabel customLabel : CustomLabel.values()){
            if (map.containsKey(String.valueOf(customLabel.getId()))){
                customLabel.setWhereCase(whereBo);
            }
        }
        for (EnumLabel enumLabel : EnumLabel.values()){
            if (map.containsKey(String.valueOf(enumLabel.getId()))){
                whereBo.getEqualMap().put(enumLabel.name().toLowerCase(),map.get(String.valueOf(enumLabel.getId())));
            }
        }
        return whereBo;
    }
}
