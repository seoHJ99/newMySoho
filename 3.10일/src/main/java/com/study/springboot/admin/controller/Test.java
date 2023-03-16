package com.study.springboot.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {

    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("data","bbbb");
        return "/client/product/test";
    }
}
