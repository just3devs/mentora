package com.mentora.backend.sms.dto;

import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsResponse {

    private String status;
    private String message;
    private String messageSid;
    private String from;
    private String to;
}