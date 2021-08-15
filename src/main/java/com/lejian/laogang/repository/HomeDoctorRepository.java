package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.HomBedBo;
import com.lejian.laogang.pojo.bo.HomeDoctorBo;
import com.lejian.laogang.repository.dao.HomeBedDao;
import com.lejian.laogang.repository.dao.HomeDoctorDao;
import com.lejian.laogang.repository.entity.HomeBedEntity;
import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


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
        return HomeDoctorBo.convert(entity);
    }

    @Override
    protected String getTableName() {
        return "home_doctor";
    }

}