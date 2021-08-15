package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.HomBedBo;
import com.lejian.laogang.repository.dao.HomeBedDao;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public class HomeBedRepository extends AbstractSpecificationRepository<HomBedBo,HomeBedEntity> {

    @Autowired
    private HomeBedDao dao;


    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    protected HomBedBo convert(HomeBedEntity entity) {
        return HomBedBo.convert(entity);
    }

    @Override
    protected String getTableName() {
        return "home_bed";
    }

}