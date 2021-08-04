package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.pojo.vo.LocationVo;
import com.lejian.laogang.repository.entity.LocationEntity;
import lombok.Data;

import java.util.Map;

@Data
public class LocationBo extends BaseBo{
    private String lng;
    private String lat;
    private Integer id;

    @Override
    public LocationEntity convert() {
        return null;
    }

    public static LocationBo convert(LocationEntity locationEntity) {
        LocationBo bo = new LocationBo();
        bo.setLat(locationEntity.getLat());
        bo.setLng(locationEntity.getLng());
        bo.setId(locationEntity.getId());
        return bo;
    }

    public LocationVo convertVo(Map<String, Long> locationMap) {
        LocationVo vo = new LocationVo();
        vo.setLat(this.lat);
        vo.setLng(this.lng);
        vo.setCount(String.valueOf(locationMap.get(String.valueOf(this.id))*10));
        return vo;
    }
}
