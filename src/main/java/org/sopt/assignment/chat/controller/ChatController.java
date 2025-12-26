package org.sopt.assignment.chat.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.chat.domain.ChatMessage;
import org.sopt.assignment.chat.domain.ConnectedUser;
import org.sopt.assignment.chat.service.ChatService;
import org.sopt.assignment.chat.service.ChatSessionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatSessionManager  chatSessionManager;

    @GetMapping("/messages")
    public List<ChatMessage> getMessages(
            @RequestParam(defaultValue = "50") int count
    ) {
        return chatService.getRecentMessages(count);
    }

    @GetMapping("/users")
    public List<ConnectedUser> getConnectedUsers() {
        return chatSessionManager.getConnectedUsers();
    }
}
