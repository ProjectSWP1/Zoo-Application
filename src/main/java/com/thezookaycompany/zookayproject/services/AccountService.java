package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.LoginDto;
import com.thezookaycompany.zookayproject.model.dto.LoginResponse;

public interface AccountService {
    String addAccount(AccountDto accountDto);

    LoginResponse loginAccount(LoginDto loginDto);
}
