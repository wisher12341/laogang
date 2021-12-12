package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.OrganBo;
import com.lejian.laogang.pojo.bo.OrganOldmanBo;
import com.lejian.laogang.repository.dao.OrganDao;
import com.lejian.laogang.repository.dao.OrganOldmanDao;
import com.lejian.laogang.repository.entity.OrganEntity;
import com.lejian.laogang.repository.entity.OrganOldmanEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrganOldmanRepository extends AbstractSpecificationRepository<OrganOldmanBo, OrganOldmanEntity> {

    @Autowired
    private OrganOldmanDao dao;

    @Override
    protected OrganOldmanBo convert(OrganOldmanEntity entity) {
        return OrganOldmanBo.convert(entity);
    }

    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    public String getTableName() {
        return "organ_oldman";
    }
}
