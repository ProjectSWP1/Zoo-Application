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

@RestController
@RequestMapping("/forgot")
public class ForgotPasswordController {

    @Autowired
    private AccountService accountService;


    @Autowired
    private EmailService emailService;


    @PostMapping("/send-email")
    public String phaseForgotPwdForm(@RequestBody AccountDto accountDto){
        // giống jsp frontend sẽ tạo 1 form chứa input type name=email để đưa email user nhập vào xử lý
        // String email = request.getParameter("email");
        // tạo 1 token mới để gửi cho user
        String token = RandomTokenGenerator.generateRandomString(20);

        try {
            // validate account and set new resetPWdToken token
            accountService.updateResetPwdToken(token, accountDto.getEmail());

            //sau này có deploy có url sẽ chỉnh lại sau
            //generate link
            String resetPwdLink = "http://localhost:8080/forgot/reset_password?token="+token;

            // send email with link
            emailService.sendEmailResetPwd(accountDto,resetPwdLink);


        } catch (AccountNotFoundException e ) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Check your email to set new Password";
    }


    @PutMapping("/reset_password")
    public String setPwd(@RequestParam String token, @RequestBody PasswordDto passwordDto) {
        // từ token lấy từ mail
        // check xem token có tồn tại kh
        // nếu tồn tại thì sẽ truy ra đc account đang giữ token đó
        Account account = accountService.getAccByPwdToken(token);

        if (account == null){
            throw new RuntimeException("Invalid Token");
        }
            // nếu account tồn tại
        else{
            // check newPwd và confirmPwd giống
            String newPassword = passwordDto.getNewPassword();
            String confirmPassword = passwordDto.getConfirmPassword();

            if (!newPassword.equals(confirmPassword)){
                throw new RuntimeException("Password not match!");
            } else {
                // update password, resetPwdToken để ngăn người dùng sử dụng lại link token cũ để đổi mk
                accountService.updatePassword(account, newPassword);
            }
        }
        return "You have changed password successfully";
    }


}
