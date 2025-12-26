package org.sopt.assignment.comment.dto.query;

import java.time.LocalDateTime;

public record CommentQueryDto(
        Long commentId,

        String name,

        LocalDateTime createdAt,

        String content,

        boolean isUpdate

) {
}
