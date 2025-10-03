package com.mentora.backend.chat.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ChatRenameDto(
        UUID chatId,
        String newTitle
) {}
