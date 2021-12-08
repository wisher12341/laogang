package com.lejian.laogang.repository;


import com.lejian.laogang.pojo.bo.PolicyBo;
import com.lejian.laogang.pojo.bo.PolicyOldmanBo;
import com.lejian.laogang.repository.dao.PolicyDao;
import com.lejian.laogang.repository.dao.PolicyOldmanDao;
import com.lejian.laogang.repository.entity.PolicyEntity;
import com.lejian.laogang.repository.entity.PolicyOldmanEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Slf4j
@Repository
public class PolicyOldmanRepository extends AbstractSpecificationRepository<PolicyOldmanBo, PolicyOldmanEntity> {

    @Autowired
    private PolicyOldmanDao dao;

    @Override
    protected PolicyOldmanBo convert(PolicyOldmanEntity policyOldmanEntity) {
        return PolicyOldmanBo.convert(policyOldmanEntity);
    }

    @Override
    protected JpaRepository getDao() {
        return dao;
    }

    @Override
    protected String getTableName() {
        return "policy_oldman";
    }

    @Transactional
    public void updateFinish(Integer finish,Integer oldmanId, Integer policyId) {
        dao.updateFinish(finish,oldmanId,policyId);
    }

    public void deleteByPolicyId(Integer id) {
        dao.deleteByPolicyId(id);
    }
}
