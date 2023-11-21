package com.thezookaycompany.zookayproject.services.impl;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.thezookaycompany.zookayproject.model.dto.EmailTokenResponse;
import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmailService;
import com.thezookaycompany.zookayproject.utils.DateFormatToSimpleDateFormat;
import com.thezookaycompany.zookayproject.utils.RandomTokenGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AccountService accountService;
    private static final String connectionString = "DefaultEndpointsProtocol=https;AccountName=cs110032002f9d9b454;AccountKey=oNT2cJxNjZ9QBPeAYe0sRYKOV54vN8zGaxpy8LemIM96nWfCdBzxahLB3V2l8AgE+loUEI2sr5Dk+AStzvPigg==;EndpointSuffix=core.windows.net";
    private static final String containerName = "qrcode";

    @Override
    public void sendEmailResetPwd(Account account, String resetPwdLink) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(account.getEmail());
        mimeMessageHelper.setSubject("[ZooKay] Please reset your password");

        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style=\"display: inline-block; border: 1px solid #ccc; padding: 20px; border-radius: 5px; text-align: center; margin: 0 auto;\">\n" +
                "        <h3 style=\"color: #122316;\">ZooKay Password Reset</h3>\n" +
                "        <p style=\"font-size: 16px; margin-bottom: 20px;\">We heard that you lost your ZooKay password. Sorry about that!</p>\n" +
                "\n" +
                "        <p style=\"font-size: 16px; margin-bottom: 20px;\">But don't worry! You can use the following button to reset your password:</p>\n" +
                "        <a href=\"" + resetPwdLink + "\" style=\"text-decoration: none;\"><button style=\"background-color: #22a168; color: #fff; border: none; padding: 10px 20px; text-align: center; font-size: 16px; margin: 10px 0; cursor: pointer;\">Reset your password</button></a>\n" +
                "\n" +
                "        <p style=\"font-size: 16px; margin-bottom: 20px;\">If you don't use this link within 3 hours, it will expire. To get a new password reset link, visit:</p>\n" +
                "        <p><a href=\"https://zookay-web.vercel.app/forgotpassword\" style=\"color: #007BFF; text-decoration: none;\">Reset my password</a></p>\n" +
                "\n" +
                "        <p>Thanks,<br>The ZooKay Devs Team</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";


        mimeMessageHelper.setText(content, true);
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
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(orders.getEmail());
        mimeMessageHelper.setSubject("[ZooKay] Ticket Purchase Confirmation");
        String visitDate = DateFormatToSimpleDateFormat.formatDateToSimpleDate(orders.getTicket().getVisitDate());

        String mailContent = "Hello " + name + "<br/>" +
                "Thank you for purchasing tickets to visit our zoo. Below are the details of your order:<br/>";
        mailContent += "<p><b> Order ID: </b>" + orders.getOrderID() + "</p>";
        mailContent += "<p><b> Ticket ID: </b>" + orders.getTicket().getTicketId() + "</p>";
        mailContent += "<p><b> Quantity: </b>" + orders.getQuantity() + "</p>";
        mailContent += "<p><b> Visit Date: </b>" + visitDate + "</p>";
        mailContent += "<p><b> Total price paid: </b>" + orders.getTicket().getTicketPrice() * orders.getQuantity() + " VND</p>";
        mailContent += "<p><b> Location: </b>Lot E2a-7, Street D1, D. D1, Long Thanh My, Thu Duc City, Ho Chi Minh City </p>";
        mailContent += "\nPlease note that this email serves as confirmation of your ticket purchase. Please keep this email as it will represent your tickets when you visit our zoo. We will use it to verify and validate your entry.\n" +
                "<br/>" +
                "We look forward to welcoming you to our zoo and hope you have an enjoyable experience.<br/>" +
                "<br/>" +
                "Sincerely,<br/>" +
                "ZOOKAY <br/>";
        // mailContent += "<hr> <img src='cid:icon' style='max-width: 100px; display: block; margin: 0 auto;'/><br/>";


        mimeMessageHelper.setText(mailContent, true);


        // Attach the QR code image
        String blobName = orders.getOrderID() + "-QRCODE.png";
        byte[] qrCodeData = getQRCodeFromBlob(blobName);
        if (qrCodeData != null) {
            mimeMessageHelper.addAttachment(blobName, new ByteArrayResource(qrCodeData));
        }
        javaMailSender.send(mimeMessage);
    }

    private byte[] getQRCodeFromBlob(String blobName) {
        try {
            // Create BlobServiceClient
            var blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

            // Create BlobContainerClient
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);

            BlockBlobClient blockBlobClient = blobContainerClient.getBlobClient(blobName).getBlockBlobClient();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blockBlobClient.download(outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public EmailTokenResponse sendVertificationEmail(Account account) throws MessagingException {
        // Create and save OTP
        String otp = RandomTokenGenerator.generateRandomOTP();
        accountService.updateVerifyToken(otp, account);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setTo(account.getEmail());
        mimeMessageHelper.setSubject("[ZooKay] Verify Your Email Address");

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4;\">\n" +
                "\n" +
                "    <div style=\"display: inline-block; border: 1px solid #ccc; padding: 20px; border-radius: 5px; text-align: center; margin: 0 auto; max-width: 600px; background-color: #fff;\">\n" +
                "\n" +
                "        <p style=\"color: #122316;\">ZooKay - Verify Your Email</p>\n" +
                "\n" +
                "        <p style=\"color: black;\">Thank you for registering an account with us. To complete the registration process and verify your email address, please follow the steps below:</p>\n" +
                "\n" +
                "        <div>\n" +
                "            <p style=\"font-size: 16px; margin-bottom: 20px; color: black;\">Here is your verification OTP:</p>\n" +
                "            <h3 style=\"color: #122316; margin: 0; color: black;\">" + otp + "</h3>\n" +
                "            <p style=\"font-size: 14px; margin-bottom: 20px; font-weight: bold; color: black;\">This OTP will expire in 2 minutes.</p>\n" +
                "        </div>\n" +
                "\n" +
                "        <p style=\"font-size: 16px; margin-bottom: 20px; color: black;\">Once you've verified your email address, you'll be able to access your account and start using our services.</p>\n" +
                "\n" +
                "        <p style=\"font-size: 16px; margin-bottom: 20px; color: black;\">If you didn't initiate this registration, you can safely ignore this email.</p>\n" +
                "\n" +
                "        <p style=\"color: black;\">Thank you for joining us!</p>\n" +
                "\n" +
                "        <p style=\"margin: 0; color: black;\">Best regards,<br>The ZooKay Devs Team</p>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";

        mimeMessageHelper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);

        return new EmailTokenResponse(account.getEmail(), otp);
    }

}
