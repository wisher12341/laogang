package com.lejian.laogang.enums.label;


import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.pojo.bo.LabelBo;
import com.lejian.laogang.pojo.vo.LabelVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 一级目录
 */
@Getter
@AllArgsConstructor
public enum LabelFirst implements BusinessEnum {
    BASIC(1, "基础信息", 1),
    HEALTH(2, "健康档案", 2),
    FAMILY(3, "家庭结构", 3),
    ECONOMIC(4, "经济条件", 4),
    YL(5, "养老状态", 5),
    AREA(6, "村居委", 6),
    ;
    private Integer value;
    private String desc;
    private Integer sort;

    public static List<LabelBo> label(){
        return Arrays.stream(LabelFirst.values()).map(item->{
            LabelBo labelBo = new LabelBo();
            labelBo.setId(item.value+"");
            labelBo.setLabel(item.desc);
            labelBo.setSort(item.sort);
            return labelBo;
        }).sorted(Comparator.comparing(LabelBo::getSort)).collect(Collectors.toList());
    }

    public static Integer find(String parentName) {
        for(LabelFirst labelFirst :LabelFirst.values()){
            if (labelFirst.getDesc().equals(parentName)){
                return labelFirst.getValue();
            }
        }
        return null;
    }
}
