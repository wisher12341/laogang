package com.lejian.laogang.repository;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lejian.laogang.pojo.bo.LocationBo;
import com.lejian.laogang.pojo.bo.OrganBo;
import com.lejian.laogang.repository.dao.LocationDao;
import com.lejian.laogang.repository.dao.OrganDao;
import com.lejian.laogang.repository.entity.LocationEntity;
import com.lejian.laogang.repository.entity.OrganEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class OrganRepository extends AbstractSpecificationRepository<OrganBo, OrganEntity> {

    @Autowired
    private OrganDao organDao;

    @Override
    protected OrganBo convert(OrganEntity organEntity) {
        return OrganBo.convert(organEntity);
    }

    @Override
    protected JpaRepository getDao() {
        return organDao;
    }

    @Override
    protected String getTableName() {
        return "organ";
    }
}
