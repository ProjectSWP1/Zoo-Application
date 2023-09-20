package com.thezookaycompany.zookayproject.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class MainController {

    @RequestMapping("/home")
    public String home() {
        return "hello there";
    }

}
