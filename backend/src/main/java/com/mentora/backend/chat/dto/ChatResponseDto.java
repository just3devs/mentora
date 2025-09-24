package com.mentora.backend.chat.dto;

import com.mentora.backend.chat.model.Message;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ChatResponseDto(
        UUID chatId,
        List<Message> newMessages
) {}
