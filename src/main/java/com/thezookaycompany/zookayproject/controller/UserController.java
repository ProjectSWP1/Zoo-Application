package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/membership")
public class UserController {

    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/home")
    public String viewHomePage() {
        return "index";
    }

}
