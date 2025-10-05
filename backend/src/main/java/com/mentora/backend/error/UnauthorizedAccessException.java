package com.mentora.backend.error;

/**
 * Exception thrown when a user attempts to access a resource they don't own.
 * This is used for authorization failures (403 Forbidden).
 */
public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}