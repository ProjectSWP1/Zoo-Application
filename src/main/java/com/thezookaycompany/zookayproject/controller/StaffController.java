package com.thezookaycompany.zookayproject.controller;


import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
@CrossOrigin
public class StaffController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String helloStaff() {
        return "Staff access";
    }

    @GetMapping("/view-trainer")
    public List<Account> getALlTrainer (@RequestParam String roleID){

        return accountRepository.findAllByRole(roleID);
    }
}
