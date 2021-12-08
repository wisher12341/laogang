package com.lejian.laogang.enums;

import com.google.common.collect.Lists;
import com.lejian.laogang.pojo.vo.MenuVo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.lejian.laogang.pojo.vo.MenuVo.messgae;

/**
 * 账号 枚举
 */
public interface UserEnum extends BusinessEnum{


    /**
     * 服务人员类型
     */
    @Getter
    @AllArgsConstructor
    enum Role implements UserEnum {
        A1(1,"管理员") {
            @Override
            public List<MenuVo> getMenu() {
                return MenuVo.getOldmanMenu();
            }
        },
        A2(2,"村/居委") {
            @Override
            public List<MenuVo> getMenu() {
                return MenuVo.getOldmanMenu();
            }
        },
        A3(3,"社区事务受理中心") {
            @Override
            public List<MenuVo> getMenu() {
                return MenuVo.getOrganOldmanMenu();
            }
        },
        A4(4,"社建办") {
            @Override
            public List<MenuVo> getMenu() {
                return MenuVo.getOrganOldmanMenu();
            }
        },
        A5(5,"卫生院") {
            @Override
            public List<MenuVo> getMenu() {
                return MenuVo.getOrganOldmanMenu();
            }
        },
        A6(6,"养老机构") {
            @Override
            public List<MenuVo> getMenu() {
                return MenuVo.getOrganMenu();
            }
        },
        A7(7,"居家养老") {
            @Override
            public List<MenuVo> getMenu() {
                return MenuVo.getOrganOldmanMenu();
            }
        },

        A99(99,"大屏展示") {
            @Override
            public List<MenuVo> getMenu() {
                return Lists.newArrayList();
            }
        },
        ;
        private Integer value;
        private String desc;


        public static UserEnum find(Integer role) {
            for (Role r : Role.values()){
                if (r.getValue().intValue()==role){
                    return r;
                }
            }
            return null;
        }

        public abstract List<MenuVo> getMenu();
    }
}
