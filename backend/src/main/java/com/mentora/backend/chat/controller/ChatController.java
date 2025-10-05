package com.mentora.backend.chat.controller;

import com.mentora.backend.chat.dto.ChatListDto;
import com.mentora.backend.chat.dto.ChatRenameDto;
import com.mentora.backend.chat.dto.ChatRenameRequest;
import com.mentora.backend.chat.dto.ChatResponseDto;
import com.mentora.backend.chat.service.ChatListService;
import com.mentora.backend.chat.service.ChatService;
import com.mentora.backend.security.CustomOAuth2UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final ChatListService chatListService;

    public ChatController(ChatService chatService, CustomOAuth2UserService oAuth2UserService, ChatListService chatListService) {
        this.chatService = chatService;
        this.chatListService = chatListService;
        this.oAuth2UserService = oAuth2UserService;
    }

    @GetMapping("/chats_history")
    public ResponseEntity<Map<String, List<ChatListDto>>> getChatsHistory() {
        String currentUserId = oAuth2UserService.currentUserId();
        Map<String, List<ChatListDto>> chatListMap = chatListService.getChatList(currentUserId);
        return ResponseEntity.ok(chatListMap);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ChatListDto>> searchChats(@RequestParam(required = false) String title) {
        String currentUserId = oAuth2UserService.currentUserId();
        List<ChatListDto> searchResults = chatService.searchChatsByTitle(currentUserId, title);
        return ResponseEntity.ok(searchResults);
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<Void> deleteChatId(@PathVariable @NotNull UUID chatId) {
        String currentUserId = oAuth2UserService.currentUserId();
        chatService.deleteChatById(currentUserId, chatId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/rename/{chatId}")
    public ResponseEntity<ChatRenameDto> renameChat(
            @PathVariable @NotNull UUID chatId,
            @Valid @RequestBody ChatRenameRequest request
    ) {
        String currentUserId = oAuth2UserService.currentUserId();
        ChatRenameDto responseDto = chatService.chatRenameById(currentUserId, chatId, request.getNewTitle());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/history/{chatId}")
    public ResponseEntity<ChatResponseDto> getChatHistory(@PathVariable @NotNull UUID chatId) {
        String currentUserId = oAuth2UserService.currentUserId();
        ChatResponseDto chatHistory = chatService.getChatHistoryById(currentUserId, chatId);
        return ResponseEntity.ok(chatHistory);
    }

    @DeleteMapping("/deleteAllChats/")
    public ResponseEntity<String> deleteAllChats() {
        String currentUserId = oAuth2UserService.currentUserId();
        chatService.deleteAllChatsByUserId(currentUserId);
        return ResponseEntity.ok("All Chats Deleted Successfully");
    }
}