package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmailService;
import com.thezookaycompany.zookayproject.utils.RandomTokenGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AccountService accountService;
    @Override
    public void sendEmailResetPwd(AccountDto accountDto, String resetPwdLink) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(accountDto.getEmail());
        mimeMessageHelper.setSubject("[ZooKay] Please reset your password");

        //               **** CHỈNH SỬA LINK HREF SAU *****
//        String content = "" +
//                " <div style=\"display: inline-block; border: 1px solid #ccc; padding: 20px; border-radius: 5px; text-align: center;\">\n" +
//                "        <h3 style=\"color: #122316;\">ZooKay Password Reset</h3>\n" +
//                "        <p style=\"font-size: 16px; margin-bottom: 20px;\">We heard that you lost your ZooKay password. Sorry about that!</p>\n" +
//                "\n" +
//                "        <p style=\"font-size: 16px; margin-bottom: 20px;\">But don't worry! You can use the following button to reset your password:</p>\n" +
//                "        <a href=\"http://localhost:8080/#\" style=\"text-decoration: none;\"><button style=\"background-color: #22a168; color: #fff; border: none; padding: 10px 20px; text-align: center; font-size: 16px; margin: 10px 0; cursor: pointer;\">Reset your password</button></a>\n" +
//                "\n" +
//                "        <p style=\"font-size: 16px; margin-bottom: 20px;\">If you don't use this link within 3 hours, it will expire. To get a new password reset link, visit:</p>\n" +
//                "        <p><a href=\"http://localhost:8080/baitedhaha\" style=\"color: #007BFF; text-decoration: none;\">https://ZookayOfficial.com/password_reset</a></p>\n" +
//                "\n" +
//                "        <p>Thanks,<br>The ZooKay Devs Team</p>\n" +
//                "    </div>".formatted(accountDto.getEmail(),true);
        String content1 ="Hello, we are Zookay Dev Teams, we heard that you lost your ZooKay password. Sorry about that!\n" +
                "But don't worry! You can use the following link to reset your password:\n" +
                 resetPwdLink +"\n "+
                "Thanks,\n" +
                "The ZooKay Devs Team\\n\"";

        mimeMessageHelper.setText(content1);
        javaMailSender.send(mimeMessage);
    }


    @Override
    public void sendVertificationEmail(AccountDto accountDto) throws AccountNotFoundException {
        //create and save otp
        String otp = RandomTokenGenerator.generateRandomOTP();
        accountService.updateVerifyToken(otp,accountDto.getEmail());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(accountDto.getEmail());
        simpleMailMessage.setSubject("[ZooKay] Verify Your Email Address");

        String link = "http://localhost:8080/user/verify?email="+accountDto.getEmail()+"&otp="+otp;

        // ** SAU DEPLOY SẼ SỬA LẠI LINK VỚI FORMAT HTML **
        String content = "Hello,\n" +
                "\n" +
                "Thank you for registering an account with us. To complete the registration process and verify your email address, please follow the steps below:\n" +
                "\n" +
                "Here is your vertification link:\n"
                + link +

                "\n" +
                "Once you've verified your email address, you'll be able to access your account and start using our services.\n" +
                "\n" +
                "If you didn't initiate this registration, you can safely ignore this email.\n" +
                "\n" +
                "Thank you for joining us!\n" +
                "\n" +
                "Best regards,\n" +
                "ZooKay Devs Team\n";

        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }

}
