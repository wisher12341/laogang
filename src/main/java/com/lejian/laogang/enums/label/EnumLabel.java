package com.lejian.laogang.enums.label;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanAttrEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.pojo.bo.LabelBo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum EnumLabel implements LabelEnum {


    /**
     * 命名跟数据库字段一致
     */
    MALE(4, 1, 999, OldmanEnum.Male.values()),
    FAMILY_TYPE(5, 3, 999, OldmanAttrEnum.FamilyType.values()),
    IS_ZD(6, 1, 999, OldmanEnum.IS_ZD.values())
    ;

    private Integer id;
    private Integer parent;
    private Integer sort;
    private BusinessEnum[] enumValues;


    @Override
    public List<LabelBo> label() {
        return Arrays.stream(this.enumValues).map(item->{
            LabelBo labelBo = new LabelBo();
            labelBo.setId(this.id+"_"+item.getValue());
            labelBo.setParent(this.parent);
            labelBo.setLabel(item.getDesc());
            labelBo.setSort(this.sort);
            return labelBo;
        }).collect(Collectors.toList());
    }


}