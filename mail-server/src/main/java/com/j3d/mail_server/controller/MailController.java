package com.j3d.mail_server.controller;

import com.j3d.mail_server.dto.MailRequestDto;
import com.j3d.mail_server.service.MailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@Valid @RequestBody MailRequestDto mailRequest) {
        try {
            mailService.sendMail(mailRequest);
            return ResponseEntity.ok("Mail başarıyla gönderildi → " + mailRequest.getTo());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("Mail gönderilemedi: " + e.getMessage());
        }
    }
}
