package com.lejian.laogang.repository.dao;


import com.lejian.laogang.repository.entity.PolicyEntity;
import com.lejian.laogang.repository.entity.PolicyOldmanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PolicyOldmanDao extends JpaRepository<PolicyOldmanEntity, Long>,JpaSpecificationExecutor<PolicyOldmanEntity> {

    @Modifying
    @Query(value = "update policy_oldman set finish=?1 where oldman_id=?2 and policy_id=?3",nativeQuery = true)
    void updateFinish(Integer finish,Integer oldmanId, Integer policyId);

    @Modifying
    @Query(value = "delete from policy_oldman policy_id=?1",nativeQuery = true)
    void deleteByPolicyId(Integer id);
}
