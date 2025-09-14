package com.mentora.backend.error;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
public class FileValidationException extends RuntimeException {
    private final List<FileError> errors;

    public FileValidationException(String message, List<FileError> errors) {
        super(message);
        this.errors = errors;
    }

    @Builder
    @Getter
    public static class FileError {
        private String fileName;
        private String errorCode;
        private String message;
        private Long maxSize;
    }
}