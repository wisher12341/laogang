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

    @GetMapping("/oldman")
    public ModelAndView oldman(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/oldman");
        return mv;
    }

}
