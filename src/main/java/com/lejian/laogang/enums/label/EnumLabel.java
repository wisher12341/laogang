package com.lejian.laogang.enums.label;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanAttrEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.pojo.bo.LabelBo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum EnumLabel implements LabelBaseEnum {


    /**
     * oldman 表 命名跟数据库字段一致
     */
    MALE(4, 1, 999, OldmanEnum.Male.values()),
    FAMILY_TYPE(5, 3, 999, OldmanAttrEnum.FamilyType.values()),
    FAMILY(19, 3, 999, OldmanAttrEnum.Faimly.values()),
    IS_ZD(6, 1, 999, OldmanEnum.IS_ZD.values()),
    HUJI(7,1,999,OldmanEnum.Huji.values()),
    politics(8,1,999,OldmanEnum.politics.values()),
    A26(14,2,999,OldmanEnum.eyesight.values()),
    A27(15,2,999,OldmanEnum.psychosis.values()),
    A31(16,4,999,OldmanAttrEnum.Income.values()),
    A51(18,5,999,OldmanAttrEnum.ServiceStatus.values())
    ;

    private Integer id;
    private Integer parent;
    private Integer sort;
    private OldmanAttrEnum.labelEnum[] enumValues;


    @Override
    public List<LabelBo> label() {
        return Arrays.stream(this.enumValues).map(item->{
            LabelBo labelBo = new LabelBo();
            labelBo.setId(this.id+"_"+item.getValue());
            labelBo.setParent(this.parent);
            labelBo.setLabel(item.getDesc());
            if (StringUtils.isNotBlank(item.getName())){
                labelBo.setLabel(item.getName());
            }
            labelBo.setSort(this.sort);
            labelBo.setDisplay(item.getDisplay());
            return labelBo;
        }).collect(Collectors.toList());
    }


}