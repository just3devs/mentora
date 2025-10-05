package com.mentora.backend.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for chat rename requests with validation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRenameRequest {

    public static final String TITLE_PATTERN = "^[a-zA-Z0-9\\s\\-_.,!?()]+$";

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Pattern(
            regexp = ChatRenameRequest.TITLE_PATTERN,
            message = "Title contains invalid characters. Only letters, numbers, spaces, and basic punctuation are allowed"
    )
    private String newTitle;
}