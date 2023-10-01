package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AccountRepository accountRepository;

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

    @PostMapping("/findUser")
    public Account getUser(@RequestBody AccountDto accountDto) {
        return accountRepository.findOneByEmail(accountDto.getEmail());
    }

    //For login user please write this json in ReactJS
    //        const loginDto = {
    //              email: email,
    //              password: password,
    //        };
    @PostMapping(path = "/login")
    public LoginResponse loginUser(@RequestBody LoginDto loginDto) {
        return accountService.loginAccount(loginDto);
    }

    @PostMapping("/register")
    public Account registerUser(@RequestBody AccountDto accountDto, MemberDto memberDto) {
        return accountService.addAccount(accountDto, memberDto);
    }

    @PostMapping("/send-email")
    public String processSendMailWithToken(@RequestBody AccountDto accountDto){

        //send mail with token
        try {
            emailService.sendVertificationEmail(accountDto);
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "Please check your mail to get Vertification link";
    }

    @PutMapping("/verify")
    public String verifyAccWithToken (@RequestParam String email, @RequestParam String otp){
        Account account = accountRepository.findAccountByEmail(email);
        if(account !=null){
            accountService.verifyAccount(account.getEmail(), otp);
        } else {
            throw new RuntimeException("Invalid OTP or OTP had expired");
        }
        return "Account verified successfully";
    }
}
