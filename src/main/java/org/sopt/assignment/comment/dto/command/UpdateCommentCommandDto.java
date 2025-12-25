package org.sopt.assignment.comment.dto.command;

import org.sopt.assignment.comment.dto.request.UpdateCommentRequestDto;

public record UpdateCommentCommandDto(
        Long articleId,

        Long commentId,

        String content,

        Long memberId
) {
    public static UpdateCommentCommandDto of(Long articleId,
                                             Long commentId,
                                             UpdateCommentRequestDto request,
                                             Long memberId) {
        return new UpdateCommentCommandDto(articleId, commentId, request.comment(), memberId);
    }
}
