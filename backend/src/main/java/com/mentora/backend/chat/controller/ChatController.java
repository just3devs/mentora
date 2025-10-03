package com.mentora.backend.chat.controller;

//import com.mentora.backend.auth.service.AuthService;
import com.mentora.backend.chat.dto.ChatListDto;
import com.mentora.backend.chat.dto.ChatRenameDto;
import com.mentora.backend.chat.dto.ChatResponseDto;
import com.mentora.backend.chat.dto.UserMessageDto;
import com.mentora.backend.chat.service.ChatListService;
import com.mentora.backend.chat.service.ChatService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
   // private final AuthService authService;
    private final ChatListService chatListService;

//    public ChatController(ChatService chatService, AuthService authService, ChatListService chatListService, AgentGateway agentGateway) {
//
//        this.chatService = chatService;
//        this.authService = authService;
//        this.chatListService = chatListService;
//    }

//    @GetMapping("/chats_history")
//    public ResponseEntity<Map<String, List<ChatListDto>>> getChatsHistory() {
//        String currentUserId = authService.currentUserId();
//        Map<String, List<ChatListDto>> chatListMap = chatListService.getChatList(currentUserId);
//        return ResponseEntity.ok(chatListMap);
//
//    }

//    @GetMapping("/search")
//    public ResponseEntity<List<ChatListDto>> searchChats(@RequestParam(required = false) String title) {
//        String currentUserId = authService.currentUserId();
//        List<ChatListDto> searchResults = chatService.searchChatsByTitle(currentUserId, title);
//        return ResponseEntity.ok(searchResults);
//    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<Void> deleteChatId(@PathVariable UUID chatId) {
        try {
            chatService.DeleteChatById(chatId);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/rename/{chatId}")
    public ResponseEntity<ChatRenameDto> renameChat(@PathVariable UUID chatId, @RequestParam String newTitle) {
        ChatRenameDto responseDto = chatService.ChatRenameById(chatId, newTitle);
        return ResponseEntity.ok(responseDto);
    }


//    @GetMapping("/history/{chatId}")
//    public ResponseEntity<ChatResponseDto> getChatHistory(@PathVariable UUID chatId) {
//        String currentUserId = authService.currentUserId();
//        ChatResponseDto chatHistory = chatService.getChatHistoryById(currentUserId, chatId);
//        return ResponseEntity.ok(chatHistory);
//    }

//    @DeleteMapping("/deleteAllChats/")
//    public ResponseEntity<String> deleteAllChats() {
//        String currentUserId = authService.currentUserId();
//        chatService.deleteAllChatsByUserId(currentUserId);
//        return ResponseEntity.ok("All Chats Deleted Successfully");
//    }



}
