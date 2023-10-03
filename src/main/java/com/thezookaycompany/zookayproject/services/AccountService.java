package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.Account;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountService {
    String addAccount(AccountDto accountDto, MemberDto memberDto);

    boolean assignRoleToAccount(Account account, String role_id);

    LoginResponse loginAccount(LoginDto loginDto);

    void updateResetPwdToken (String token, String email) throws AccountNotFoundException;

    Account getAccByPwdToken(String resetPwdToken);

    void updatePassword(Account account, String newPassword);

    void updateVerifyToken (String token, String email) throws AccountNotFoundException;

    void verifyAccount(String email, String otp);

}
