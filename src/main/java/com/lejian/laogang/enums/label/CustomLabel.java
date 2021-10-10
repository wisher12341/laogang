package com.lejian.laogang.enums.label;


import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.LabelBo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 自定义标签
 */
@Getter
@AllArgsConstructor
public enum CustomLabel implements LabelBaseEnum {
    AGE_60_69(1, 1, 1, "60-69",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            String start=getDesc().split("-")[0];
            String end=getDesc().split("-")[1];
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)).toLocalDate());
        }
    },
    AGE_70_79(5, 1, 1, "70-79",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            String start=getDesc().split("-")[0];
            String end=getDesc().split("-")[1];
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)).toLocalDate());
        }
    },
    AGE_80_89(19, 1, 1, "80-89",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            String start=getDesc().split("-")[0];
            String end=getDesc().split("-")[1];
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            jpaSpecBo.getGreatEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)).toLocalDate());
        }
    },
    A21(10, 2, 1, "有慢病",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getViewLabelLike().add("%@10_%");
        }
    },
    A22(8, 2, 1, "有失能情况",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getViewLabelLike().add("%@8_%");
        }
    },
    A23(9, 2, 1, "有药物过敏",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getViewLabelLike().add("%@9_%");
        }
    },
    A24(11, 2, 1, "有肿瘤史",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getViewLabelLike().add("%@11_%");
        }
    },
    A25(12, 2, 1, "有病情既往史",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getViewLabelLike().add("%@12_%");
        }
    },
    ;

    private Integer id;
    private Integer parent;
    private Integer sort;
    private String desc;
    private Boolean display;

    public abstract void setWhereCase(JpaSpecBo jpaSpecBo);


    @Override
    public List<LabelBo> label() {
        LabelBo labelBo = new LabelBo();
        labelBo.setId(this.id+"");
        labelBo.setLabel(getDesc());
        labelBo.setParent(this.parent);
        labelBo.setSort(this.sort);
        labelBo.setDisplay(this.display);
        return Lists.newArrayList(labelBo);
    }
}