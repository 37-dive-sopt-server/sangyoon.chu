package org.sopt.assignment.chat.domain;

public record ConnectedUser(
        Long userId,

        String userName,

        String sessionId
) {
    public static ConnectedUser of(Long userId, String userName, String sessionId) {
        return new ConnectedUser(userId, userName, sessionId);
    }
}
