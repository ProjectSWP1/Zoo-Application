package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.RoleRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.MemberServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberServices memberServices;


    @Override
    public String addAccount(AccountDto accountDto, MemberDto memberDto) {
        //Check trùng mail
        Account temp = accountRepository.findOneByEmail(accountDto.getEmail().trim());
        if(temp != null) {
            return "This account " + accountDto.getEmail() + " has been registered.";
        }
        // Parse lại Email thành Username
        accountDto.setUsername(accountDto.getEmail().trim().split("@")[0]);
        // Add member trước rồi mới add account
        memberServices.addMember(accountDto, memberDto);
        Account acc = new Account(
                accountDto.getUsername(),
                this.passwordEncoder.encode(accountDto.getPassword()),
                accountDto.getEmail(),
                memberRepository.findMemberByPhoneNumber(memberDto.getPhoneNumber()),
                roleRepository.findByRoleName("Member")
        );
        accountRepository.save(acc);
        return "New account " + accountDto.getEmail() + " has been added";
    }

    @Override
    public boolean assignRoleToAccount(Account account, String role_id) {
        if (account.getRole() != null) {
            account.setRole(roleRepository.findRoleByRoleID(role_id));
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    public LoginResponse loginAccount(LoginDto loginDto) {
        // Check username or email that user input?
        if(!loginDto.getEmail().contains("@")) {
            String username = loginDto.getEmail();
        }
        return null;
    }
}
