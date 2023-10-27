package com.thezookaycompany.zookayproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CLientForwardController {

    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String forward(){
        return "forward:/";
    }
}
