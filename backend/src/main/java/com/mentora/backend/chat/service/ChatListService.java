package com.mentora.backend.chat.service;

import com.mentora.backend.chat.dto.ChatListDto;
import com.mentora.backend.chat.model.Chat;
import com.mentora.backend.chat.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatListService {
    private final ChatRepository chatRepository;

    public ChatListService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Map<String, List<ChatListDto>> getChatList(String userId) {
        List<Chat> chatList = chatRepository.findByUserIdOrderByUpdatedAtDesc(userId);

        Map<String, List<ChatListDto>> listMap = new LinkedHashMap<>();

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        for (Chat chat : chatList) {
            LocalDate chatDate = chat.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            ChatListDto dto = toChatListDto(chat);

            String key;
            if (chatDate.equals(today)) {
                key = "Today";
            } else if (chatDate.equals(yesterday)) {
                key = "Yesterday";
            } else {
                key = "Previous Chats";
            }

            listMap.computeIfAbsent(key, k -> new ArrayList<>()).add(dto);
        }

        return listMap;
    }

    private ChatListDto toChatListDto(Chat chat) {
        return ChatListDto.builder().chatId(chat.getId()).title(chat.getTitle()).build();
    }

}
