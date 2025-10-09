package com.j3d.mail_server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MailRequestDto {

    @NotBlank(message = "Alıcı mail adresi boş olamaz")
    @Email(message = "Geçerli bir mail adresi giriniz")
    private String to;

    @NotBlank(message = "Konu boş olamaz")
    private String subject;

    @NotBlank(message = "Mesaj içeriği boş olamaz")
    private String text;

    public MailRequestDto() {}

    public MailRequestDto(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
