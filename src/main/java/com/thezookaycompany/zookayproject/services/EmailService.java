package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmailResetPwd(AccountDto accountDto, String resetPwdLink) throws MessagingException;
    void sendEmailWithNudes(AccountDto accountDto);

    void sendVertificationEmail (Account account);
}
