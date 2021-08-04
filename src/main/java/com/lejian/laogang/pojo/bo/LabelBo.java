package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.pojo.vo.LabelVo;
import lombok.Data;

@Data
public class LabelBo {
    private String id;
    private Integer parent;
    private String label;
    private Integer sort;

    public LabelVo convert() {
        LabelVo labelVo = new LabelVo();
        labelVo.setId(this.id);
        labelVo.setLabel(this.label);
        labelVo.setParent(this.parent);
        return labelVo;
    }
}
