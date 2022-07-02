package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.LogBo;
import com.lejian.laogang.pojo.bo.PolicyBo;
import com.lejian.laogang.repository.dao.LogDao;
import com.lejian.laogang.repository.dao.PolicyDao;
import com.lejian.laogang.repository.entity.LogEntity;
import com.lejian.laogang.repository.entity.PolicyEntity;
import com.lejian.laogang.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static com.lejian.laogang.util.DateUtils.YYMMDDHHMMSS;

@Slf4j
@Repository
public class LogRepository extends AbstractSpecificationRepository<LogBo, LogEntity> {

    @Autowired
    private LogDao dao;

    @Override
    protected LogBo convert(LogEntity entity) {
        LogBo logBo = new LogBo();
        BeanUtils.copyProperties(entity,logBo);
        logBo.setCreateTime(DateUtils.format(entity.getCreateTime(),YYMMDDHHMMSS));
        return logBo;
    }

    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    public String getTableName() {
        return "log";
    }
}
