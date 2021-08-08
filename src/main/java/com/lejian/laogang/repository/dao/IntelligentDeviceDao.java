package com.lejian.laogang.repository.dao;


import com.lejian.laogang.repository.entity.HomeDoctorEntity;
import com.lejian.laogang.repository.entity.IntelligentDeviceEntity;
import com.lejian.laogang.repository.entity.OldmanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface IntelligentDeviceDao extends JpaRepository<IntelligentDeviceEntity, Long>,JpaSpecificationExecutor<IntelligentDeviceEntity> {

}
