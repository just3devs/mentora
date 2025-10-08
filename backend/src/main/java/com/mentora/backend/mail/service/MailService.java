package com.mentora.backend.mail.service;

import com.mentora.backend.mail.dto.MailRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendMail(MailRequestDto mailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("j3dmentora@gmail.com");
        message.setTo(mailRequest.getTo());
        message.setSubject(mailRequest.getSubject());
        message.setText(mailRequest.getText());
        mailSender.send(message);
    }

}