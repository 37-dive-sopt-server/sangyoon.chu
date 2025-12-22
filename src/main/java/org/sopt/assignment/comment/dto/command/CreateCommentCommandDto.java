package org.sopt.assignment.comment.dto.command;

import org.sopt.assignment.comment.dto.request.CreateCommentRequestDto;

public record CreateCommentCommandDto(
        String comment,

        Long articleId,

        Long userId
) {
    public static CreateCommentCommandDto of(CreateCommentRequestDto requestDto, Long articleId, Long userId) {
        return new CreateCommentCommandDto(requestDto.comment(), articleId, userId);
    }
}
