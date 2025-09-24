package com.mentora.backend.chat.service;


import com.mentora.backend.chat.dto.ChatRenameDto;
import com.mentora.backend.chat.dto.ChatResponseDto;
import com.mentora.backend.chat.dto.UserMessageDto;
import com.mentora.backend.chat.model.Chat;
import com.mentora.backend.chat.model.Message;
import com.mentora.backend.chat.repository.ChatRepository;
import com.mentora.backend.enums.MessageSender;
import com.mentora.backend.chat.dto.ChatListDto;
import jakarta.transaction.Transactional;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    private Chat createNewChat(String userId, String text) {
        Chat chat = new Chat();
        chat.setUserId(userId);
        chat.setTitle(generateTitleFromMessage(text));
        return chat;
    }

    private Chat findExistingChat(UUID chatId) {
        return chatRepository.findById(chatId).orElseThrow(() -> new NoSuchElementException("Chat not found."));
    }


    private Message createUserMessage(String messageText, String modelName) {
        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setSender(MessageSender.USER);
        message.setText(messageText);
        message.setTimestamp(new Date());
        return message;
    }

    private Message createAssistantMessage(String assistantText, String modelName) {
        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setSender(MessageSender.ASSISTANT);
        message.setText(assistantText);
        message.setTimestamp(new Date());
        return message;
    }

    private String generateTitleFromMessage(String messageText) {
        if (messageText == null || messageText.trim().isEmpty()) return "";
        String[] words = messageText.trim().split("\\s+");
        return String.join(" ", Arrays.copyOfRange(words, 0, Math.min(words.length, 3)));
    }

    @Transactional
    public void DeleteChatById(UUID id) {
        if (chatRepository.existsById(id)) {
            chatRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Chat not found.");
        }
    }

    @Transactional
    public void deleteAllChatsByUserId(String userId) {
        chatRepository.deleteAllByUserId(userId);
    }

    @Transactional
    public ChatRenameDto ChatRenameById(UUID id, String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        Chat chat = chatRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Chat not found."));

        chat.setTitle(title.trim());
        chatRepository.save(chat);

        return ChatRenameDto.builder().chatId(chat.getId()).newTitle(chat.getTitle()).build();
    }

    public List<ChatListDto> searchChatsByTitle(String userId, String title) {

        List<Chat> chats;

        if (title == null || title.trim().isEmpty()) {
            chats = chatRepository.findByUserIdOrderByUpdatedAtDesc(userId);
            if (chats.size() > 5) {
                chats = chats.subList(0, 5);
            }
        } else {
            chats = chatRepository.findByUserIdAndTitleContainingIgnoreCaseOrderByUpdatedAtDesc(userId, title);
        }

        return chats.stream().map(chat -> ChatListDto.builder().chatId(chat.getId()).title(chat.getTitle()).build()).collect(Collectors.toList());
    }


    public ChatResponseDto getChatHistoryById(String userId, UUID chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));


        if (!chat.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to chat");
        }

        return ChatResponseDto.builder().chatId(chat.getId()).newMessages(chat.getMessages()).build();
    }

}