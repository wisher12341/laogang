package com.lejian.laogang.util;

import com.lejian.laogang.pojo.bo.UserBo;
import com.lejian.laogang.repository.UserRepository;
import com.lejian.laogang.security.UserContext;
import org.springframework.security.core.userdetails.User;

public class UserUtils {

    public static UserBo getUser(){
        User user = UserContext.getLoginUser();
        if (user == null){
            return null;
        }
        UserRepository userRepository = BeanUtils.getBean(UserRepository.class);
        return userRepository.getByUsername(user.getUsername());
    }

    public static String getUsername(){
        User user = UserContext.getLoginUser();
        if (user == null){
            return "";
        }
        return user.getUsername();
    }
}
