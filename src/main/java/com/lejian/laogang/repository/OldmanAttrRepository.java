package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.JpaSpecBo;
import com.lejian.laogang.pojo.bo.OldmanAttrBo;
import com.lejian.laogang.pojo.bo.OldmanBo;
import com.lejian.laogang.repository.dao.OldmanAttrDao;
import com.lejian.laogang.repository.dao.OldmanDao;
import com.lejian.laogang.repository.entity.OldmanAttrEntity;
import com.lejian.laogang.repository.entity.OldmanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Map;

import static com.lejian.laogang.common.ComponentRespCode.REPOSITORY_ERROR;


@Repository
public class OldmanAttrRepository extends AbstractSpecificationRepository<OldmanAttrBo,OldmanAttrEntity> {

    @Autowired
    private OldmanAttrDao dao;


    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    protected OldmanAttrBo convert(OldmanAttrEntity entity) {
        return OldmanAttrBo.convert(entity);
    }

    @Override
    protected String getTableName() {
        return "oldman_attr";
    }

    public Long typeCount(JpaSpecBo jpaSpecBo) {
        try {
            String where = jpaSpecBo.getSql();
            String sql = "select count(distinct oldman_id) from oldman_attr where "+where;

            Query query =entityManager.createNativeQuery(sql);
            return Long.valueOf(String.valueOf(query.getResultList().get(0)));

        }catch (Exception e){
            REPOSITORY_ERROR.doThrowException("typeCount",e);
        }
        return 0L;
    }
}
