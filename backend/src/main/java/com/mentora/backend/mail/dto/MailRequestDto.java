package com.mentora.backend.mail.dto;

import lombok.Getter;

@Getter
public class MailRequestDto {
    private String to;
    private String subject;
    private String text;


}
