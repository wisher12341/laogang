package com.lejian.laogang.repository.dao;


import com.lejian.laogang.repository.entity.OldmanAttrEntity;
import com.lejian.laogang.repository.entity.OldmanViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface OldmanViewDao extends JpaRepository<OldmanViewEntity, String>,JpaSpecificationExecutor<OldmanViewEntity> {

}
