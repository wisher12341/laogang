package com.lejian.laogang.pojo.vo;

import com.google.common.collect.Lists;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 菜单
 */
@Data
@AllArgsConstructor
public class MenuVo {
    private static final MenuVo OLDMAN = new MenuVo("老人管理","/home/oldman");
    private static final MenuVo POLICY = new MenuVo("政策管理","/home/policy");
    private static final MenuVo ACCOUNT = new MenuVo("账号管理","/home/account");
    private static final MenuVo ORGAN_INFO = new MenuVo("信息管理","/home/organ/info");
    private static final MenuVo ORGAN_OLDMAN = new MenuVo("老人管理","/home/organ/oldman");

    private String name;
    private String url;
    private Integer count;

    public MenuVo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public static MenuVo oldman(){
        return OLDMAN;
    }

    public static MenuVo policy() {
        return POLICY;
    }

    public static MenuVo account(){
        return ACCOUNT;
    }
    public static MenuVo organInfo(){
        return ORGAN_INFO;
    }
    public static MenuVo organOldman(){
        return ORGAN_OLDMAN;
    }

    public static MenuVo messgae() {
        return new MenuVo("收件箱","/home/message");
    }

    public static List<MenuVo> getOldmanMenu(){
        return Lists.newArrayList(
                oldman(),
                policy(),
                messgae(),
                account());
//        return Lists.newArrayList(oldman());
    }

    public static List<MenuVo> getOrganOldmanMenu(){
        return Lists.newArrayList(
                oldman(),
                messgae(),
                account());
//        return Lists.newArrayList(oldman());
    }


    public static List<MenuVo> getOrganMenu(){
        return Lists.newArrayList(
                organInfo(),
                organOldman(),
                messgae(),
                account());
//        return Lists.newArrayList(
//                organInfo(),
//                organOldman());
    }
}
