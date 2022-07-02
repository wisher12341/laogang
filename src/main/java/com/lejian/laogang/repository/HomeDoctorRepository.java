package com.lejian.laogang.repository;


import com.lejian.laogang.log.LogRecord;
import com.lejian.laogang.pojo.bo.HomBedBo;
import com.lejian.laogang.pojo.bo.HomeDoctorBo;
import com.lejian.laogang.repository.dao.HomeBedDao;
import com.lejian.laogang.repository.dao.HomeDoctorDao;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@LogRecord(table = "home_doctor")
@Repository
public class HomeDoctorRepository extends AbstractSpecificationRepository<HomeDoctorBo,HomeDoctorEntity> {

    @Autowired
    private HomeDoctorDao dao;


    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    protected HomeDoctorBo convert(HomeDoctorEntity entity) {
        if(entity == null){
            return null;
        }
        return HomeDoctorBo.convert(entity);
    }

    @Override
    public String getTableName() {
        return "home_doctor";
    }

    public HomeDoctorBo findByOldmanId(Integer id) {
        return convert(dao.findByOldmanId(id));
    }
}
