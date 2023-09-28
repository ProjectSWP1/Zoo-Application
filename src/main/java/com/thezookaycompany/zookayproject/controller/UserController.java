package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.MemberServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;

@RestController
@CrossOrigin("*")
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
    public Account saveAccount(@RequestBody RequestWrapper requestWrapper) {
        return accountService.addAccount(requestWrapper.getAccountDto(), requestWrapper.getMemberDto());
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

    @GetMapping("/login/oauth2")
    public OAuth2AuthenticationToken googleLogin(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return oAuth2AuthenticationToken;
    }
    @Autowired
    private  MemberRepository memberRepository;
    @Autowired
    private  MemberServices memberServices;
    @GetMapping
    public List<Member> getAllMember(){

        return memberRepository.findAll();
    }
    @GetMapping("/member/{phoneNumber}")
    public Member findMemberByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {

        return memberRepository.findMemberByPhoneNumber(phoneNumber);
    }
    @PutMapping("/member/{phoneNumber}")
    public Member updateMember(@PathVariable("phoneNumber") String phoneNumber, @RequestBody Member member){
        member.setPhoneNumber(phoneNumber);
         return memberServices.updateMember(member);
    }

}
