package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.LocationBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.repository.dao.LocationDao;
import com.lejian.laogang.repository.dao.OldmanDao;
import com.lejian.laogang.repository.entity.LocationEntity;
import com.lejian.laogang.repository.entity.OldmanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class LocationRepository extends AbstractSpecificationRepository<LocationBo,LocationEntity> {

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
    protected String getTableName() {
        return "location";
    }

}
