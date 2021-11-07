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
            jpaSpecBo.getGreatMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)+1).toLocalDate());
        }
    },
    AGE_70_79(5, 1, 1, "70-79",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            String start=getDesc().split("-")[0];
            String end=getDesc().split("-")[1];
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            jpaSpecBo.getGreatMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)+1).toLocalDate());
        }
    },
    AGE_80_89(19, 1, 1, "80-89",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            String start=getDesc().split("-")[0];
            String end=getDesc().split("-")[1];
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            jpaSpecBo.getGreatMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)+1).toLocalDate());
        }
    },
    AGE_90_99(191, 1, 1, "90-99",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            String start=getDesc().split("-")[0];
            String end=getDesc().split("-")[1];
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
            jpaSpecBo.getGreatMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(end)+1).toLocalDate());
        }
    },
    AGE_100(192, 1, 1, "100-",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            String start="100";
            jpaSpecBo.getLessEMap().put("birthday", LocalDateTime.now().minusYears(Integer.valueOf(start)).toLocalDate());
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
    A31(31, 6, 1, "牛肚村",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("areaVillage",getDesc());
        }
    },
    A32(32, 6, 1, "中港村",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("areaVillage",getDesc());
        }
    },
    A33(33, 6, 1, "成日村",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("areaVillage",getDesc());
        }
    },
    A34(34, 6, 1, "建港村",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("areaVillage",getDesc());
        }
    },
    A35(35, 6, 1, "东河村",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("areaVillage",getDesc());
        }
    },
    A36(36, 6, 1, "大河村",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("areaVillage",getDesc());
        }
    },
    A37(37, 6, 1, "欣河村",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("areaVillage",getDesc());
        }
    },
    A38(38, 6, 1, "老港居委",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("areaVillage",getDesc());
        }
    },
    A39(39, 6, 1, "滨海居委",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("areaVillage",getDesc());
        }
    },
    A310(310,6, 1, "宏港苑居委",true) {
        @Override
        public void setWhereCase(JpaSpecBo jpaSpecBo) {
            jpaSpecBo.getEqualMap().put("areaVillage",getDesc());
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