package com.lejian.laogang.repository.dao;


import com.lejian.laogang.repository.entity.MessageEntity;
import com.lejian.laogang.repository.entity.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageDao extends JpaRepository<MessageEntity, Long>,JpaSpecificationExecutor<MessageEntity> {

}
