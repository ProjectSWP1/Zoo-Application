package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.EmailTokenResponse;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmailService;
import com.thezookaycompany.zookayproject.utils.RandomTokenGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AccountService accountService;
    @Override
    public void sendEmailResetPwd(Account account, String resetPwdLink) throws MessagingException {


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(account.getEmail());
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
    /*
    Subject: Ticket Purchase Confirmation

Hello [User's Name],

Thank you for purchasing tickets to visit our zoo. Below are the details of your tickets:

Ticket ID: [Ticket ID]
Visit Date: [Visit Date]
Location: [Zoo Location]

Please note that this email serves as confirmation of your ticket purchase. Please keep this email as it will represent your tickets when you visit our zoo. We will use the ticket ID to verify and validate your entry.

We look forward to welcoming you to our zoo and hope you have an enjoyable experience.

Sincerely,
[Your Name]
[Your Zoo Name]

     */

    @Override
    public void sendAfterPaymentEmail(OrdersDto ordersDto) throws MessagingException {
        Orders orders = ordersRepository.findOrdersByOrderID(ordersDto.getOrderID());
        String name = orders.getEmail().trim().split("@")[0];
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setTo(orders.getEmail());
        mimeMessageHelper.setSubject("[ZooKay] Ticket Purchase Confirmation");

        String mailContent = "Hello "+name+"<br/>" +
                "Thank you for purchasing tickets to visit our zoo. Below are the details of your order:<br/>";
        mailContent += "<p><b> Order ID: </b>"+orders.getOrderID()+"</p>";
        mailContent += "<p><b> Ticket ID: </b>"+orders.getTicket().getTicketId()+"</p>";
        mailContent += "<p><b> Quantity: </b>"+orders.getQuantity()+"</p>";
        mailContent += "<p><b> Visit Date: </b>"+orders.getTicket().getVisitDate()+"</p>";
        mailContent += "<p><b> Total order: </b>"+orders.getTicket().getTicketPrice() * orders.getQuantity()+"</p>";
        mailContent += "<p><b> Location: </b>Lot E2a-7, Street D1, D. D1, Long Thanh My, Thu Duc City, Ho Chi Minh City </p>";
        mailContent +="\nPlease note that this email serves as confirmation of your ticket purchase. Please keep this email as it will represent your tickets when you visit our zoo. We will use the ticket ID to verify and validate your entry.\n" +
                "<br/>" +
                "We look forward to welcoming you to our zoo and hope you have an enjoyable experience.<br/>" +
                "<br/>" +
                "Sincerely,<br/>" +
                "ZOOKAY <br/>";
        mailContent += "<hr> <img src='cid:icon'/><br/>";

        mimeMessageHelper.setText(mailContent,true);
        ClassPathResource resource = new ClassPathResource("zookay_icon.png");
        mimeMessageHelper.addInline("icon",resource);
        javaMailSender.send(mimeMessage);
    }


    @Override
    public EmailTokenResponse sendVertificationEmail(Account account) {
        //create and save otp
        String otp = RandomTokenGenerator.generateRandomOTP();
        accountService.updateVerifyToken(otp,account);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(account.getEmail());
        simpleMailMessage.setSubject("[ZooKay] Verify Your Email Address");

       // String link = "http://localhost:8080/user/verify?email="+accountDto.getEmail()+"&otp="+otp;

        // ** SAU DEPLOY SẼ SỬA LẠI LINK **
        String content = "Hello,\n" +
                "\n" +
                "Thank you for registering an account with us. To complete the registration process and verify your email address, please follow the steps below:\n" +
                "\n" +
                "Here is your vertification OTP:\n"
                + otp +

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
        return new EmailTokenResponse(account.getEmail(),otp);
    }

}
