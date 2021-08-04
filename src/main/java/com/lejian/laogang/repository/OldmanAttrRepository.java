package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.repository.dao.OldmanDao;
import com.lejian.laogang.repository.entity.OldmanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public class OldmanAttrRepository extends AbstractSpecificationRepository<OldmanBo,OldmanEntity> {

    @Autowired
    private OldmanDao oldmanDao;


    @Override
    protected JpaRepository getDao() {
        return oldmanDao;
    }

    @Override
    protected OldmanBo convert(OldmanEntity oldmanEntity) {
        return OldmanBo.convert(oldmanEntity);
    }

    @Override
    protected String getTableName() {
        return "oldman_attr";
    }
}
