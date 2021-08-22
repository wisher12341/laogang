package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.repository.entity.HistoryEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class HistoryBo  extends BaseBo{
    private String time;
    private Integer value;
    private Integer type;
    private Long count;

    @Override
    public <Entity> Entity convert() {
        return null;
    }


    public static HistoryBo convert(HistoryEntity entity) {
        HistoryBo bo = new HistoryBo();
        BeanUtils.copyProperties(entity,bo);
        return bo;
    }
}
