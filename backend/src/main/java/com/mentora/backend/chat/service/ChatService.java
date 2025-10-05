package com.mentora.backend.chat.service;

import com.mentora.backend.chat.dto.ChatRenameDto;
import com.mentora.backend.chat.dto.ChatResponseDto;
import com.mentora.backend.chat.dto.ChatListDto;
import com.mentora.backend.chat.model.Chat;
import com.mentora.backend.chat.model.Message;
import com.mentora.backend.chat.repository.ChatRepository;
import com.mentora.backend.enums.MessageSender;
import com.mentora.backend.error.ChatNotFoundException;
import com.mentora.backend.error.UnauthorizedAccessException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException(chatId));
    }

    /**
     * Validates that the current user owns the chat.
     * Throws UnauthorizedAccessException if the user doesn't own the chat.
     *
     * @param chat the chat to validate
     * @param userId the current user's ID
     * @throws UnauthorizedAccessException if user doesn't own the chat
     */
    private void validateChatOwnership(Chat chat, String userId) {
        if (!chat.getUserId().equals(userId)) {
            throw new UnauthorizedAccessException(
                    "You don't have permission to access this chat"
            );
        }
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

    /**
     * Deletes a chat by ID with authorization check.
     *
     * @param userId the current user's ID
     * @param chatId the ID of the chat to delete
     * @throws ChatNotFoundException if chat not found
     * @throws UnauthorizedAccessException if user doesn't own the chat
     */
    @Transactional
    public void deleteChatById(String userId, UUID chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException(chatId));

        validateChatOwnership(chat, userId);

        chatRepository.delete(chat);
    }

    /**
     * Deletes all chats belonging to a user.
     *
     * @param userId the user's ID
     */
    @Transactional
    public void deleteAllChatsByUserId(String userId) {
        chatRepository.deleteAllByUserId(userId);
    }

    /**
     * Renames a chat with authorization check and validation.
     *
     * @param userId the current user's ID
     * @param chatId the ID of the chat to rename
     * @param newTitle the new title (already validated by controller)
     * @return ChatRenameDto containing the updated chat info
     * @throws ChatNotFoundException if chat not found
     * @throws UnauthorizedAccessException if user doesn't own the chat
     */
    @Transactional
    public ChatRenameDto chatRenameById(String userId, UUID chatId, String newTitle) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException(chatId));

        validateChatOwnership(chat, userId);

        chat.setTitle(newTitle.trim());
        chatRepository.save(chat);

        return ChatRenameDto.builder()
                .chatId(chat.getId())
                .newTitle(chat.getTitle())
                .build();
    }

    /**
     * Searches chats by title for a specific user.
     *
     * @param userId the user's ID
     * @param title the search query (optional)
     * @return list of matching chats
     */
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

        return chats.stream()
                .map(chat -> ChatListDto.builder()
                        .chatId(chat.getId())
                        .title(chat.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Gets chat history with authorization check.
     *
     * @param userId the current user's ID
     * @param chatId the chat ID
     * @return ChatResponseDto containing chat history
     * @throws ChatNotFoundException if chat not found
     * @throws UnauthorizedAccessException if user doesn't own the chat
     */
    public ChatResponseDto getChatHistoryById(String userId, UUID chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException(chatId));

        validateChatOwnership(chat, userId);

        return ChatResponseDto.builder()
                .chatId(chat.getId())
                .newMessages(chat.getMessages())
                .build();
    }
}