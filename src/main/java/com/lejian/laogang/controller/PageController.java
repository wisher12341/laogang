package com.lejian.laogang.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/index");
        return mv;
    }

    @GetMapping("/visual")
    public ModelAndView visual(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/visual");
        return mv;
    }

    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home");
        return mv;
    }

    @GetMapping("/home/oldman")
    public ModelAndView home_query(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/home/oldman");
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
