package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.services.EmailService;
import com.thezookaycompany.zookayproject.utils.RandomTokenGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JavaMailSender javaMailSender;
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
                 resetPwdLink +
                "\nThanks," +
                "The ZooKay Devs Team\\n\"";

        mimeMessageHelper.setText(content1);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendEmailWithNudes(AccountDto accountDto) {

        //send email vertification
    }

    @Override
    public void sendVertificationEmail(Account account) {
        //create and save token
        String token = RandomTokenGenerator.generateRandomString(15);
        account.setVertificationToken(token);
        accountRepository.save(account);

    }

}
