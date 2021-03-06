package com.lejian.laogang.repository.dao;

import com.lejian.laogang.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity,Long>,JpaSpecificationExecutor<UserEntity> {

    UserEntity findByUsernameAndPassword(String username, String password);

    UserEntity findByUsername(String username);
}
