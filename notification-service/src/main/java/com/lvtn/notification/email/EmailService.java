package com.lvtn.notification.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    public String sendSimpleEmail(EmailDetails emailDetails){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailSender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setSubject(emailDetails.getSubject());


            javaMailSender.send(mailMessage);
            System.out.println("test done");
            return "mail sent successfully";
        }
        catch (Exception e){
            e.printStackTrace();
            return "mail sent error";
        }

    }

    public String sendEmailWithAttachments(EmailDetails emailDetails){
        return  null;
    }



}
