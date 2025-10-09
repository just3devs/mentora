package com.j3d.mail_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to,
                         String subject,
                         String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gberke2003@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        System.out.println("Mail Sent Sucessfully");
    }
}
