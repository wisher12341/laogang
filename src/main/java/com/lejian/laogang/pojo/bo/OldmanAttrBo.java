package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.pojo.vo.LocationVo;
import com.lejian.laogang.repository.entity.LocationEntity;
import com.lejian.laogang.repository.entity.OldmanAttrEntity;
import lombok.Data;

import java.util.Map;

@Data
public class OldmanAttrBo extends BaseBo{
    private Integer oldmanId;
    private BusinessEnum type;
    private BusinessEnum value;
    private String ext;
    private String idCard;


    @Override
    public OldmanAttrEntity convert() {
        OldmanAttrEntity entity = new OldmanAttrEntity();
        entity.setIdCard(this.idCard);
        entity.setExt(this.ext);
        entity.setOldmanId(this.oldmanId);
        entity.setType(this.type.getValue());
        entity.setValue(this.value.getValue());
        return entity;
    }

    public static OldmanAttrBo convert(OldmanAttrEntity entity) {
        OldmanAttrBo bo = new OldmanAttrBo();

        return bo;
    }


}
