package com.lejian.laogang.repository.dao;


import com.lejian.laogang.repository.entity.HistoryEntity;
import com.lejian.laogang.repository.entity.LocationEntity;
import com.lejian.laogang.repository.entity.OldmanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HistoryDao extends JpaRepository<HistoryEntity, Long>,JpaSpecificationExecutor<HistoryEntity> {

}
