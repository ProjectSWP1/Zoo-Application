package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Role;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.RoleRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.MemberServices;
import com.thezookaycompany.zookayproject.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.ScatteringByteChannel;
import java.util.List;

@Service
@Transactional
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MemberServices memberServices;


    @Override
    public Account addAccount(AccountDto accountDto, MemberDto memberDto) {
        // Parse lại Email thành Username
        accountDto.setUsername(accountDto.getEmail().trim().split("@")[0]);
        String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
        Role userRole = roleRepository.findByRoleName("Member").get();

        // Add member trước rồi mới add account
        memberServices.addMember(accountDto, memberDto);
        Account acc = new Account(
                accountDto.getUsername(),
                encodedPassword,
                accountDto.getEmail(),
                memberRepository.findMemberByPhoneNumber(memberDto.getPhoneNumber()),
                userRole
        );
        return accountRepository.save(acc);
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
        String username = "";
        if(loginDto.getEmail().contains("@")) {
            username = loginDto.getEmail().trim().split("@")[0];
        }
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, loginDto.getPassword())
            );
            // DEBUG: ko thể tìm thấy account
            Account acc = accountRepository.findByUsername(username).get();
            String token = tokenService.generateJwt(auth);

            return new LoginResponse(acc, token);


        } catch(AuthenticationException e) {
            return new LoginResponse(null, "");
        }
    }
}
