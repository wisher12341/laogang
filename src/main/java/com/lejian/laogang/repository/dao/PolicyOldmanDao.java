package com.lejian.laogang.repository.dao;


import com.lejian.laogang.repository.entity.PolicyEntity;
import com.lejian.laogang.repository.entity.PolicyOldmanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface PolicyOldmanDao extends JpaRepository<PolicyOldmanEntity, Long>,JpaSpecificationExecutor<PolicyOldmanEntity> {

}
