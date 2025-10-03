package com.mentora.backend.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.mentora.backend.enums.MessageSender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private UUID id;
    private MessageSender sender;
    private String text;
    private Date timestamp;
}
