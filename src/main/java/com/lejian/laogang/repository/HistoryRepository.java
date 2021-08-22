package com.lejian.laogang.repository;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lejian.laogang.pojo.bo.HistoryBo;
import com.lejian.laogang.pojo.bo.LocationBo;
import com.lejian.laogang.repository.dao.HistoryDao;
import com.lejian.laogang.repository.dao.LocationDao;
import com.lejian.laogang.repository.entity.HistoryEntity;
import com.lejian.laogang.repository.entity.LocationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class HistoryRepository extends AbstractSpecificationRepository<HistoryBo, HistoryEntity> {

    @Autowired
    private HistoryDao historyDao;


    @Override
    protected JpaRepository getDao() {
        return historyDao;
    }

    @Override
    protected HistoryBo convert(HistoryEntity entity) {
        return HistoryBo.convert(entity);
    }

    @Override
    protected String getTableName() {
        return "history";
    }

}
