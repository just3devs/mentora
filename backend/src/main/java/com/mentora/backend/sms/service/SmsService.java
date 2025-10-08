package com.mentora.backend.sms.service;

import com.mentora.backend.sms.config.TwilioConfig;
import com.mentora.backend.sms.dto.SmsRequest;
import com.mentora.backend.sms.dto.SmsResponse;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class SmsService {

    private final TwilioConfig twilioConfig;

    public SmsResponse sendSms(SmsRequest smsRequest) {
        log.info("Sending SMS to: {}", smsRequest.getPhoneNumber());

        try {
            Message message = Message.creator(
                    new PhoneNumber(smsRequest.getPhoneNumber()),
                    new PhoneNumber(twilioConfig.getPhoneNumber()),
                    smsRequest.getMessage()
            ).create();

            log.info("SMS sent successfully. SID: {}", message.getSid());

            return SmsResponse.builder()
                    .status("SUCCESS")
                    .message("SMS sent successfully")
                    .messageSid(message.getSid())
                    .from(message.getFrom().toString())
                    .to(message.getTo())
                    .build();

        } catch (Exception e) {
            log.error("Failed to send SMS to {}: {}", smsRequest.getPhoneNumber(), e.getMessage());
            throw new RuntimeException("Failed to send SMS: " + e.getMessage(), e);
        }
    }
}