package com.lejian.laogang.repository;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lejian.laogang.pojo.bo.LocationBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.repository.dao.LocationDao;
import com.lejian.laogang.repository.dao.OldmanDao;
import com.lejian.laogang.repository.entity.LocationEntity;
import com.lejian.laogang.repository.entity.OldmanEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class LocationRepository extends AbstractSpecificationRepository<LocationBo, LocationEntity> {

    @Autowired
    private LocationDao locationDao;


    @Override
    protected JpaRepository getDao() {
        return locationDao;
    }

    @Override
    protected LocationBo convert(LocationEntity locationEntity) {
        return LocationBo.convert(locationEntity);
    }

    @Override
    public String getTableName() {
        return "location";
    }

    /**
     * 批量获取location id，
     * 如果不存在则添加
     *
     * @param locationBoList
     * @return
     */
    public Map<String, Integer> getBatchByDescOrCreate(List<LocationBo> locationBoList) {
        Map<String, Integer> map = Maps.newHashMap();

        Map<String, LocationBo> boMap = Maps.newHashMap();

        locationBoList.forEach(bo -> {
            String key = bo.getLng() + "_" + bo.getLat();
            if (!boMap.containsKey(key)){
                boMap.put(key,bo);
            }
        });

        locationBoList.forEach(bo -> map.put(bo.getLng() + "_" + bo.getLat(), null));


        List<List<LocationBo>> list = Lists.partition(locationBoList,150);
        list.forEach(l->{
            try {
                List<String> keyList = l.stream()
                        .map(item -> item.getLng() + "_" + item.getLat()).distinct().collect(Collectors.toList());
                //先从数据库查询已有的
                List<LocationEntity> locationEntities = locationDao.findByPkeyIn(keyList);
                Map<String, Integer> locationEntityMap = locationEntities.stream().collect(Collectors.toMap(LocationEntity::getPkey, LocationEntity::getId));

                map.putAll(locationEntityMap);
            }catch (Exception e){
                log.warn("fail ");
            }
        });

        //新增
        map.forEach((k, v) -> {
            if (v == null) {
                map.put(k, create(boMap.get(k)));
            }
        });
        return map;
    }

    private Integer create(LocationBo locationBo) {
        LocationEntity locationEntitySave = new LocationEntity();
        locationEntitySave.setDescription(locationBo.getDesc());
        locationEntitySave.setLng(locationBo.getLng());
        locationEntitySave.setLat(locationBo.getLat());
        locationEntitySave.setPkey(locationBo.getLng()+"_"+locationBo.getLat());
        LocationEntity result = locationDao.save(locationEntitySave);
        return result.getId();
    }
}
