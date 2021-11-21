package com.lejian.laogang.service;


import com.lejian.laogang.controller.contract.request.PageParam;
import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.UserEnum;
import com.lejian.laogang.pojo.bo.UserBo;
import com.lejian.laogang.pojo.vo.UserVo;
import com.lejian.laogang.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

    public List<UserVo> getUserByPage(PageParam pageParam) {
        return userRepository.findByPageWithSpec(pageParam.getPageNo(),pageParam.getPageSize(),null).stream().map(this::convert).collect(Collectors.toList());
    }

    private UserVo convert(UserBo userBo) {
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(userBo,userVo);
        userVo.setPassword("********");
        userVo.setRoleDesc(BusinessEnum.find(userBo.getRole(), UserEnum.Role.class).getDesc());
        return userVo;
    }

    public Long getCount() {
        return userRepository.countWithSpec(null);
    }


    public UserBo getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
