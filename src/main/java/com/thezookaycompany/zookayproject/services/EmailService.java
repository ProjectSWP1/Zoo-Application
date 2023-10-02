package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import jakarta.mail.MessagingException;

import javax.security.auth.login.AccountNotFoundException;

public interface EmailService {
    void sendEmailResetPwd(AccountDto accountDto, String resetPwdLink) throws MessagingException;

    void sendVertificationEmail (AccountDto accountDto) throws AccountNotFoundException;
}
