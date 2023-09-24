package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountService accountService;

    //Register for users, you should leave this json
    //    const requestData = {
    //        accountDto: {
    //            // AccountDto fields here
    //        },
    //        memberDto: {
    //            // MemberDto fields here
    //        }
    //    };
    @GetMapping(path = "/")
    public String userAccess() {
        return "User accessed";
    }
    @PostMapping(path = "/save")
    public String saveAccount(@RequestBody RequestWrapper requestWrapper) {
        return accountService.addAccount(requestWrapper.getAccountDto(), requestWrapper.getMemberDto());
    }

}
