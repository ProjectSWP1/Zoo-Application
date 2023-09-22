package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/membership/save")
    public String newUser(@RequestBody Account acc) {
//        Long empId = Long.valueOf(acc.getEmpId());
//        Employees e = getEmployeesID(empId);
//        if(e != null) {
            accountRepository.save(acc);
            return "New user has been added";
//        }
//        return "User cannot be added";
    }

}
