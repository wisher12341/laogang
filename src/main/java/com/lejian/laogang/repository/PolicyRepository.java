package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.OrganBo;
import com.lejian.laogang.pojo.bo.PolicyBo;
import com.lejian.laogang.repository.dao.OrganDao;
import com.lejian.laogang.repository.dao.PolicyDao;
import com.lejian.laogang.repository.entity.OrganEntity;
import com.lejian.laogang.repository.entity.PolicyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class PolicyRepository extends AbstractSpecificationRepository<PolicyBo, PolicyEntity> {

    @Autowired
    private PolicyDao dao;

    @Override
    protected PolicyBo convert(PolicyEntity policyEntity) {
        return PolicyBo.convert(policyEntity);
    }

    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    public String getTableName() {
        return "policy";
    }
}
