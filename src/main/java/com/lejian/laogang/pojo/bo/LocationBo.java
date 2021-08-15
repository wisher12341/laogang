package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.pojo.vo.LocationVo;
import com.lejian.laogang.repository.entity.LocationEntity;
import lombok.Data;

import javax.persistence.Column;
import java.util.Map;

@Data
public class LocationBo extends BaseBo{
    private String lng;
    private String lat;
    private Integer id;
    private String desc;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LocationBo that = (LocationBo) o;

        if (lng != null ? !lng.equals(that.lng) : that.lng != null) return false;
        return lat != null ? lat.equals(that.lat) : that.lat == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        return result;
    }

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
