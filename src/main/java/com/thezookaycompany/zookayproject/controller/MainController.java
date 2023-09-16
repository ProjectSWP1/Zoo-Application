package com.thezookaycompany.zookayproject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/home")
    public String home() {
        return "hello there";
    }

}
