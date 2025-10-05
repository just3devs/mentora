package com.mentora.backend.error;

import java.util.UUID;

/**
 * Exception thrown when a chat is not found.
 * This is used for 404 Not Found responses.
 */
public class ChatNotFoundException extends RuntimeException {

    public ChatNotFoundException(UUID chatId) {
        super("Chat not found: " + chatId);
    }

    public ChatNotFoundException(String message) {
        super(message);
    }

    public ChatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}