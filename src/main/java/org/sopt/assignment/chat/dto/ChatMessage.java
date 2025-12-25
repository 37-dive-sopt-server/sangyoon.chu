package org.sopt.assignment.chat.dto;

import java.time.LocalDateTime;

public record ChatMessage(
    MessageType type,

    String sender,

    String content,

    LocalDateTime timestamp


) {
    public enum MessageType{
        CHAT,
        ENTER,
        LEAVE
    }

    public static ChatMessage of(MessageType type, String sender, String content) {
        return new ChatMessage(type, sender, content, LocalDateTime.now());
    }
}
