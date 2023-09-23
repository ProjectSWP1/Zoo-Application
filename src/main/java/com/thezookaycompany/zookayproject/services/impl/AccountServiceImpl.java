package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.model.entity.Role;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.RoleRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
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


    @Override
    public String addAccount(AccountDto accountDto) {
        // Parse lại Email thành Username
        String username = accountDto.getEmail().trim().split("@")[0];
        Account acc = new Account(
                username,
                this.passwordEncoder.encode(accountDto.getPassword()),
                accountDto.getEmail(),
                roleRepository.findByRoleName("Member"),
                // Đang thiếu bên param Member

        );
        return null;
    }

    @Override
    public LoginResponse loginAccount(LoginDto loginDto) {
        return null;
    }
}
