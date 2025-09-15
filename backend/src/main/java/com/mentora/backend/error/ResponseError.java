package com.mentora.backend.error;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseError {
    private String errorCode;
    private List<ErrorMessage> errorMessages;

    @Data
    @Builder
    public static class ErrorMessage {
        private String field;
        private String message;
    }
}