package com.mentora.backend.chat.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ChatListDto(
        UUID chatId,
        String title
) {}
