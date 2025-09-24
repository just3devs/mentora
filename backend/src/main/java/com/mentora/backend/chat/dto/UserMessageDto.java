package com.mentora.backend.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserMessageDto(
        UUID chatId,
        @NotBlank(message = "message text cannot be empty")
        String messageText,
        String modelName

        
) {}

