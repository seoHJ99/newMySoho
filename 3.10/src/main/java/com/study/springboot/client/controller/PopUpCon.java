package com.study.springboot.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PopUpCon {

    @RequestMapping("agreement1")
    public String agreement1(){
        return "client/theOthers/popUp-agreement2";
    }

    @RequestMapping("agreement2")
    public String agreement2(){
        return "client/theOthers/popUp-agreement";
    }

    @RequestMapping("agreement3")
    public String agreement3(){
        return "client/theOthers/popUp-private";
    }

    @RequestMapping("agreement4")
    public String agreement4(){
        return "client/theOthers/popUp-private2";
    }
}
