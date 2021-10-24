package com.lejian.laogang.repository.dao;


import com.lejian.laogang.repository.entity.HomeBedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface HomeBedDao extends JpaRepository<HomeBedEntity, Long>,JpaSpecificationExecutor<HomeBedEntity> {

    HomeBedEntity findByOldmanId(Integer id);
}
