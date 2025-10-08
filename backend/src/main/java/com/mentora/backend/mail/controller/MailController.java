package com.mentora.backend.mail.controller;

import com.mentora.backend.mail.dto.MailRequestDto;
import com.mentora.backend.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody MailRequestDto mailRequest) {
        try {
            mailService.sendMail(mailRequest);
            return ResponseEntity.ok("Mail başarıyla gönderildi → " + mailRequest.getTo());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Mail gönderilemedi: " + e.getMessage());
        }
    }

}
