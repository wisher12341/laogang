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
            jpaSpecBo.getLessEMap().put("o.birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            jpaSpecBo.getGreatEMap().put("o.birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)).toLocalDate());
        }
    },
    AGE_70_79(2, 1, 1, "70-79",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            String start=getDesc().split("-")[0];
            String end=getDesc().split("-")[1];
            jpaSpecBo.getLessEMap().put("o.birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            jpaSpecBo.getGreatEMap().put("o.birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)).toLocalDate());
        }
    },
    AGE_80_89(3, 1, 1, "80-89",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            String start=getDesc().split("-")[0];
            String end=getDesc().split("-")[1];
            jpaSpecBo.getLessEMap().put("o.birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            jpaSpecBo.getGreatEMap().put("o.birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)).toLocalDate());
        }
    },
    A21(9, 2, 1, "有慢病",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("oa.type","10");
        }
    },
    A22(10, 2, 1, "有失能情况",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("oa.type","8");
        }
    },
    A23(11, 2, 1, "有药物过敏",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("oa.type","9");
        }
    },
    A24(12, 2, 1, "有肿瘤史",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("oa.type","11");
        }
    },
    A25(13, 2, 1, "有病情既往史",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("oa.type","12");
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