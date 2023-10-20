package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.PasswordDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmailService;
import com.thezookaycompany.zookayproject.utils.RandomTokenGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/forgot")
public class ForgotPasswordController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String phaseForgotPwdForm(@RequestBody AccountDto accountDto) throws MessagingException {
        // giống jsp frontend sẽ tạo 1 form chứa input type name=email để đưa email user nhập vào xử lý
        // String email = request.getParameter("email");
        // tạo 1 token mới để gửi cho user
        Account account = accountService.getUserByEmail(accountDto.getEmail());
        if (account == null) {
            return "Couldn't find account with email: "+accountDto.getEmail();
        }
        String token = RandomTokenGenerator.generateRandomString(20);

            // validate account and set new resetPWdToken token
            accountService.updateResetPwdToken(token, account);

            //sau này có deploy có url sẽ chỉnh lại sau
            //generate link
            String resetPwdLink = "http://localhost:8080/forgot/reset_password?token="+token;

            // send email with link
            emailService.sendEmailResetPwd(account,resetPwdLink);



        return "Check your email to set new Password";
    }


    @PutMapping("/reset_password")
    public String setPwd(@RequestParam String token, @RequestBody PasswordDto passwordDto) {

        Account account = accountService.getAccByPwdToken(token);
        if (account == null) {
            return "Invalid Token or Your link has expired!";
        }

        if (passwordDto.getNewPassword() == null || passwordDto.getNewPassword().isEmpty()) {
            return "Missing input field!";
        }
        if (passwordDto.getNewPassword().length() < 8) {
            return "Password's length must be longer than 8";
        }
        String newPassword = passwordDto.getNewPassword();
        String confirmPassword = passwordDto.getConfirmPassword();

        if (!newPassword.equals(confirmPassword)) {
            return "Password not match";
        }

        accountService.updatePassword(account, newPassword);
        return "You have changed password successfully";
    }
}
