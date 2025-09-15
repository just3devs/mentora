package com.mentora.backend.error;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FileValidationErrorResponse {
    private String error;
    private String message;
    private List<FileErrorDetail> details;

    @Data
    @Builder
    public static class FileErrorDetail {
        private String fileName;
        private String error;
        private String message;
        private String maxSize;
    }
}