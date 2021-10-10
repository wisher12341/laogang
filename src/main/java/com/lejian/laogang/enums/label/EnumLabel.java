package com.lejian.laogang.enums.label;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.OldmanAttrEnum;
import com.lejian.laogang.enums.OldmanEnum;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
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
    MALE(16, 1, 999, OldmanEnum.Male.values()) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values) {
            jpaSpecBo.getEqualMap().put("male",values.get(0));
        }
    },
    FAMILY_TYPE(2, 3, 999, OldmanAttrEnum.FamilyType.values()) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values) {
            jpaSpecBo.getViewLabelLike().add("%@"+getId()+"_"+values.get(0)+"%");
        }
    },
    FAMILY(3, 3, 999, OldmanAttrEnum.Faimly.values()) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values) {
            jpaSpecBo.getViewLabelLike().add("%@"+getId()+"_"+values.get(0)+"%");
        }
    },
    IS_ZD(6, 1, 999, OldmanEnum.IS_ZD.values()) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values) {
            jpaSpecBo.getEqualMap().put("isZd",values.get(0));
        }
    },
    HUJI(7,1,999,OldmanEnum.Huji.values()) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values) {
            jpaSpecBo.getEqualMap().put("huji",values.get(0));
        }
    },
    politics(18,1,999,OldmanEnum.politics.values()) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values) {
            jpaSpecBo.getEqualMap().put("politics",values.get(0));
        }
    },
    A26(14,2,999,OldmanEnum.eyesight.values()) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values) {
            jpaSpecBo.getEqualMap().put("eyesight",values.get(0));
        }
    },
    A27(15,2,999,OldmanEnum.psychosis.values()) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values) {
            jpaSpecBo.getEqualMap().put("psychosis",values.get(0));
        }
    },
    A31(4,4,999,OldmanAttrEnum.Income.values()) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values) {
            jpaSpecBo.getViewLabelLike().add("%@"+getId()+"_"+values.get(0)+"%");
        }
    },
    A51(13,5,999,OldmanAttrEnum.ServiceStatus.values()) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values) {
            jpaSpecBo.getViewLabelLike().add("%@"+getId()+"_"+values.get(0)+"%");
        }
    };

    private Integer id; //oldman_attr 表的属性 Id 同type
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

    public abstract void setWhereCase(JpaSpecBo jpaSpecBo, List<String> values);

}