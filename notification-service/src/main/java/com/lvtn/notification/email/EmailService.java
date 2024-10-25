package com.lvtn.notification.email;

import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.common.SuccessMessage;
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
            return SuccessMessage.OK.getMessage();
        }
        catch (Exception e){
            return ErrorCode.ERROR.getMessage();
        }

    }
    public String sendEmailWithAttachments(EmailDetails emailDetails){
        return  null;
    }
}
