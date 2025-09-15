package com.mentora.backend.error;

import lombok.Getter;

import java.util.List;

@Getter
public class FileValidationException extends RuntimeException {

    private final List<FileValidationError> errors;

    public FileValidationException(String message, List<FileValidationError> errors) {
        super(message);
        this.errors = errors;
    }

    public FileValidationException(String message, FileValidationError error) {
        super(message);
        this.errors = List.of(error);
    }

    @Getter
    public static class FileValidationError {
        private final String fileName;
        private final String errorCode;
        private final String message;
        private final String maxSize;

        public FileValidationError(String fileName, String errorCode, String message) {
            this.fileName = fileName;
            this.errorCode = errorCode;
            this.message = message;
            this.maxSize = null;
        }

        public FileValidationError(String fileName, String errorCode, String message, String maxSize) {
            this.fileName = fileName;
            this.errorCode = errorCode;
            this.message = message;
            this.maxSize = maxSize;
        }
    }
}