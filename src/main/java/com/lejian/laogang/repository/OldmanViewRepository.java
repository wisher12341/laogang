package com.lejian.laogang.repository;


import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.repository.dao.OldmanDao;
import com.lejian.laogang.repository.dao.OldmanViewDao;
import com.lejian.laogang.repository.entity.OldmanEntity;
import com.lejian.laogang.repository.entity.OldmanViewEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lejian.laogang.common.ComponentRespCode.REPOSITORY_ERROR;


@Repository
public class OldmanViewRepository extends AbstractSpecificationRepository<OldmanBo,OldmanViewEntity> {

    @Autowired
    private OldmanViewDao oldmanViewDao;


    @Override
    protected OldmanBo convert(OldmanViewEntity oldmanViewEntity) {
        return OldmanBo.convert(oldmanViewEntity);
    }

    @Override
    protected JpaRepository getDao() {
        return oldmanViewDao;
    }

    @Override
    public String getTableName() {
        return "oldman_view";
    }
}
