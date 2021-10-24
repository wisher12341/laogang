package com.lejian.laogang.repository.dao;


import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface HomeDoctorDao extends JpaRepository<HomeDoctorEntity, Long>,JpaSpecificationExecutor<HomeDoctorEntity> {

    HomeDoctorEntity findByOldmanId(Integer id);
}
