package com.lejian.laogang.pojo.vo;

import lombok.Data;

@Data
public class UserVo {
    private Integer id;
    private String username;
    private String password;
    private Integer role;
    private String roleDesc;
    /**
     * 绑定的服务人员id
     */
    private Integer wid;
    private String workerName;
}
