package com.lejian.laogang.repository.dao;


import com.lejian.laogang.repository.entity.OrganEntity;
import com.lejian.laogang.repository.entity.OrganOldmanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganOldmanDao extends JpaRepository<OrganOldmanEntity, Long>,JpaSpecificationExecutor<OrganEntity> {

}
