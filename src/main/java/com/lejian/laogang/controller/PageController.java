package com.lejian.laogang.controller;


import com.lejian.laogang.pojo.bo.UserBo;
import com.lejian.laogang.pojo.vo.UserVo;
import com.lejian.laogang.security.annotation.BackOldmanAuth;
import com.lejian.laogang.util.UserUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

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

    @GetMapping("/visual")
    public ModelAndView visual(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/visual");
        return mv;
    }

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
            UserVo userVo = userBo.convertVo();
            mv.addObject("username",userVo.getUsername());
            mv.addObject("roleDesc",userVo.getRoleDesc());
            mv.addObject("role",userVo.getRole());
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
    @BackOldmanAuth
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
