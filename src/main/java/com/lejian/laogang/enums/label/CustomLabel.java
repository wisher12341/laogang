package com.lejian.laogang.enums.label;


import com.lejian.laogang.pojo.bo.LabelBo;
import com.lejian.laogang.repository.WhereBo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * 自定义标签
 */
@Getter
@AllArgsConstructor
public enum CustomLabel implements LabelEnum {
    AGE_60_69(1, 1, 1, "60_69") {
        @Override
        public String setWhereCase(WhereBo whereBo) {

            return "";
        }
    },
    AGE_70_79(1, 1, 1, "70_79") {
        @Override
        public String setWhereCase(WhereBo whereBo) {

            return "";
        }
    },
    AGE_80_89(1, 1, 1, "80_89") {
        @Override
        public String setWhereCase(WhereBo whereBo) {

            return "";
        }
    },
    ;

    private Integer id;
    private Integer parent;
    private Integer sort;
    private String desc;

    public abstract String setWhereCase(WhereBo whereBo);


    @Override
    public List<LabelBo> label() {
        LabelBo labelBo = new LabelBo();
        labelBo.setId(this.id+"");
        labelBo.setLabel(this.desc);
        labelBo.setParent(this.parent);
        labelBo.setSort(this.sort);
        return Lists.newArrayList(labelBo);
    }
}