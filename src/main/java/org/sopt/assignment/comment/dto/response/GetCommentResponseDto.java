package org.sopt.assignment.comment.dto.response;

import org.sopt.assignment.comment.domain.Comment;
import org.sopt.assignment.comment.dto.query.CommentQueryDto;
import org.sopt.assignment.comment.repository.CommentSummary;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record GetCommentResponseDto(
        Long commentId,

        String name,

        LocalDateTime createdAt,

        String content,

        boolean isUpdate
) {
    public static GetCommentResponseDto from(Comment comment) {
        return new GetCommentResponseDto(
                comment.getId(),
                comment.getMember().getName(),
                comment.getCreatedAt(),
                comment.getContent(),
                comment.isUpdate()
        );
    }

    public static GetCommentResponseDto from(CommentQueryDto commentQueryDto) {
        return new GetCommentResponseDto(
                commentQueryDto.commentId(),
                commentQueryDto.name(),
                commentQueryDto.createdAt(),
                commentQueryDto.content(),
                commentQueryDto.isUpdate()
        );
    }

    public static GetCommentResponseDto from(CommentSummary commentSummary) {
        return new GetCommentResponseDto(
                commentSummary.getId(),
                commentSummary.getMemberName(),
                commentSummary.getCreatedAt(),
                commentSummary.getContent(),
                commentSummary.isUpdate()
        );
    }

    public static GetCommentResponseDto from(Object[] result){
        return new GetCommentResponseDto(
                (Long) result[0],
                (String) result[1],
                ((Timestamp) result[2]).toLocalDateTime(),
                (String) result[3],
                (boolean) result[4]
        );
    }
}
