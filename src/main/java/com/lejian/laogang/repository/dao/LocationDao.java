package com.lejian.laogang.repository.dao;


import com.lejian.laogang.repository.entity.LocationEntity;
import com.lejian.laogang.repository.entity.OldmanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationDao extends JpaRepository<LocationEntity, Long>,JpaSpecificationExecutor<OldmanEntity> {

    List<LocationEntity> findByPkeyIn(List<String> collect);
}
