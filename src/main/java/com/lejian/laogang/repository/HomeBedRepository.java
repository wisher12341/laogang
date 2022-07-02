package com.lejian.laogang.repository;


import com.lejian.laogang.log.LogRecord;
import com.lejian.laogang.pojo.bo.HomBedBo;
import com.lejian.laogang.repository.dao.HomeBedDao;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@LogRecord(table = "home_bed")
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
        if(entity == null){
            return null;
        }
        return HomBedBo.convert(entity);
    }

    @Override
    public String getTableName() {
        return "home_bed";
    }

    public HomBedBo findByOldmanId(Integer id) {
        return convert(dao.findByOldmanId(id));
    }
}
