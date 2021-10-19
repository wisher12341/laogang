package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.LinkManBo;
import com.lejian.laogang.pojo.bo.PolicyBo;
import com.lejian.laogang.repository.dao.LinkmanDao;
import com.lejian.laogang.repository.dao.PolicyDao;
import com.lejian.laogang.repository.entity.LinkManEntity;
import com.lejian.laogang.repository.entity.PolicyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class LinkManRepository extends AbstractSpecificationRepository<LinkManBo, LinkManEntity> {

    @Autowired
    private LinkmanDao dao;

    @Override
    protected LinkManBo convert(LinkManEntity linkManEntity) {
        return LinkManBo.convert(linkManEntity);
    }

    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    protected String getTableName() {
        return "linkman";
    }
}
