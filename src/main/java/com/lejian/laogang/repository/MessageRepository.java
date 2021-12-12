package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.MessageBo;
import com.lejian.laogang.pojo.bo.PolicyBo;
import com.lejian.laogang.repository.dao.MessageDao;
import com.lejian.laogang.repository.dao.PolicyDao;
import com.lejian.laogang.repository.entity.MessageEntity;
import com.lejian.laogang.repository.entity.PolicyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class MessageRepository extends AbstractSpecificationRepository<MessageBo, MessageEntity> {

    @Autowired
    private MessageDao dao;

    @Override
    protected MessageBo convert(MessageEntity messageEntity) {
        return MessageBo.convert(messageEntity);
    }

    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    public String getTableName() {
        return "message";
    }
}
