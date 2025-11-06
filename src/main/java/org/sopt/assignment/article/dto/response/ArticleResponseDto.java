package org.sopt.assignment.article.dto.response;

import org.sopt.assignment.article.domain.Article;
import org.sopt.assignment.article.domain.ETag;

import java.time.LocalDateTime;

public record ArticleResponseDto(
        Long id,

        String title,

        String content,

        ETag tag,

        LocalDateTime createdAt,

        LocalDateTime modifiedAt)
{
    public static ArticleResponseDto from(Article article) {
        return new ArticleResponseDto(article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getTag(),
                article.getCreatedAt(),
                article.getModifiedAt());
    }
}
