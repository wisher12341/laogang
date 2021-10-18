package com.lejian.laogang.pojo.bo;

import com.lejian.laogang.enums.BusinessEnum;
import com.lejian.laogang.enums.UserEnum;
import com.lejian.laogang.pojo.vo.UserVo;
import com.lejian.laogang.repository.entity.UserEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserBo extends BaseBo{
    private Integer id;
    private String username;
    private String password;
    private Integer role;

    @Override
    public UserEntity convert() {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(this,userEntity);
        return userEntity;
    }

    public static UserBo convert(UserEntity userEntity){
        UserBo userBo = new UserBo();
        BeanUtils.copyProperties(userEntity,userBo);
        return userBo;
    }

    public UserVo convertVo() {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(this,userVo);
        userVo.setPassword("");
        userVo.setRoleDesc(BusinessEnum.find(this.getRole(), UserEnum.Role.class).getDesc());
        return userVo;
    }
}
