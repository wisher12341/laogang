package com.lejian.laogang.controller;


import com.google.gson.Gson;
import com.lejian.laogang.enums.UserEnum;
import com.lejian.laogang.job.HistoryJob;
import com.lejian.laogang.pojo.bo.UserBo;
import com.lejian.laogang.pojo.vo.MenuVo;
import com.lejian.laogang.pojo.vo.UserVo;
import com.lejian.laogang.security.annotation.BackOldmanAuth;
import com.lejian.laogang.security.annotation.VisualAuth;
import com.lejian.laogang.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PageController {

    private Gson gson = new Gson();

    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/index");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/login");
        return mv;
    }

    @VisualAuth
    @GetMapping("/visual")
    public ModelAndView visual(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/visual");
        UserBo userBo = UserUtils.getUser();
        if (userBo!=null) {
            mv.addObject("role",userBo.getRole());
        }else{
            mv.addObject("role","0");
        }
        return mv;
    }
    @Autowired
    private HistoryJob job;

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home");
        UserBo userBo = UserUtils.getUser();
        if (userBo!=null) {
            UserVo userVo = userBo.convertVo();
            mv.addObject("username",userVo.getUsername());
            mv.addObject("roleDesc",userVo.getRoleDesc());
            mv.addObject("role",userVo.getRole());
        }else{
            mv.addObject("username","");
        }
        return mv;
    }

    @BackOldmanAuth
    @GetMapping("/home/oldman")
    public ModelAndView home_query(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/oldman");
        UserBo userBo = UserUtils.getUser();
        if (userBo!=null) {
            mv.addObject("role",userBo.getRole());
        }
        return mv;
    }

    @GetMapping("/home/organ/info")
    public ModelAndView home_organ_info(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/organ_info");
        return mv;
    }


    @GetMapping("/home/organ/oldman")
    public ModelAndView home_organ_oldman(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/organ_oldman");
        return mv;
    }

    @GetMapping("/home/organ/oldman/info")
    public ModelAndView home_organ_oldman_info(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/organ_oldman_info");
        return mv;
    }

    @GetMapping("/home/base")
    public ModelAndView homebase(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/base");
        UserBo userBo = UserUtils.getUser();
        List<MenuVo>  menuVoList = ((UserEnum.Role)UserEnum.Role.find(userBo.getRole())).getMenu();
        if (userBo!=null) {
            UserVo userVo = userBo.convertVo();
            mv.addObject("username",userVo.getUsername());
            mv.addObject("roleDesc",userVo.getRoleDesc());
            mv.addObject("role",userVo.getRole());
            mv.addObject("menu",gson.toJson(menuVoList));
        }else{
            mv.addObject("username","");
        }
        return mv;
    }


    @GetMapping("/home/message")
    public ModelAndView home_message(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/message");
        return mv;
    }

    @GetMapping("/home/oldman/detail")
    public ModelAndView home_query_detail(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/oldman_detail");
        return mv;
    }
//    @BackOldmanAuth
    @GetMapping("/home/oldman/info")
    public ModelAndView home_query_info(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/oldman_info");
        UserBo userBo = UserUtils.getUser();
        if (userBo!=null) {
            UserVo userVo = userBo.convertVo();
            mv.addObject("username",userVo.getUsername());
            mv.addObject("roleDesc",userVo.getRoleDesc());
            mv.addObject("role",userVo.getRole());
        }else{
            mv.addObject("username","");
        }
        return mv;
    }

    @GetMapping("/home/organ/detail")
    public ModelAndView home_organ_detail(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/organ_detail");
        return mv;
    }

    @GetMapping("/home/policy")
    public ModelAndView home_policy(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/policy");
        UserBo userBo = UserUtils.getUser();
        if (userBo!=null) {
            mv.addObject("role",userBo.getRole());
        }
        return mv;
    }

    @GetMapping("/home/policy/info")
    public ModelAndView home_policy_info(){
        ModelAndView mv = new ModelAndView();
        UserBo userBo = UserUtils.getUser();
        if (userBo!=null) {
            mv.addObject("role",userBo.getRole());
        }
        mv.setViewName("/home/policy_info");
        return mv;
    }

    @GetMapping("/monitor")
    public ModelAndView monitor(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/monitor");
        return mv;
    }

    @GetMapping("/oldman")
    public ModelAndView oldman(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/oldman");
        return mv;
    }

}
