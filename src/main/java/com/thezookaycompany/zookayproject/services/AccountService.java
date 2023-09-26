package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.Account;

public interface AccountService {
    Account addAccount(AccountDto accountDto, MemberDto memberDto);

    boolean assignRoleToAccount(Account account, String role_id);

    LoginResponse loginAccount(LoginDto loginDto);


}
