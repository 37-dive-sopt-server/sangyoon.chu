package org.sopt.assignment.chat.domain;

import java.time.LocalDateTime;

public record ChatMessage(
    EMessageType type,

    String sender,

    String content,

    LocalDateTime timestamp

) {

    public static ChatMessage of(EMessageType type, String sender, String content) {
        return new ChatMessage(type, sender, content, LocalDateTime.now());
    }
}
