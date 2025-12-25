package org.sopt.assignment.comment.dto.response;

import org.sopt.assignment.comment.domain.Comment;

import java.time.LocalDateTime;

public record GetCommentResponseDto(
        Long commentId,

        String name,

        LocalDateTime createdAt,

        String content
) {
    public static GetCommentResponseDto from(Comment comment) {
        return new GetCommentResponseDto(
                comment.getId(),
                comment.getMember().getName(),
                comment.getCreatedAt(),
                comment.getContent()
        );
    }
}
