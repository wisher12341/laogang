package com.lejian.laogang.repository;

import com.lejian.laogang.pojo.bo.UserBo;
import com.lejian.laogang.repository.dao.UserDao;
import com.lejian.laogang.repository.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractSpecificationRepository<UserBo,UserEntity> {

    @Autowired
    private UserDao userDao;

    @Override
    protected JpaRepository getDao() {
        return userDao;
    }

    @Override
    protected UserBo convert(UserEntity userEntity) {
        return UserBo.convert(userEntity);
    }

    @Override
    protected String getTableName() {
        return "user";
    }

    public UserBo getByUsernameAndPassword(String username, String password) {
        return UserBo.convert(userDao.findByUsernameAndPassword(username,password));
    }


    public UserBo getByUsername(String username) {
        return UserBo.convert(userDao.findByUsername(username));
    }
}
