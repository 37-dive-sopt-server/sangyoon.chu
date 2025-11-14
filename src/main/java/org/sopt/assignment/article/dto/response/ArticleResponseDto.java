package org.sopt.assignment.article.dto.response;

import org.sopt.assignment.article.domain.Article;
import org.sopt.assignment.article.domain.ETag;
import org.sopt.assignment.member.domain.Member;

import java.time.LocalDateTime;

public record ArticleResponseDto(
        Long id,

        String title,

        String content,

        ETag tag,

        LocalDateTime createdAt,

        LocalDateTime modifiedAt,

        String memberName)
{
    public static ArticleResponseDto of(Article article) {
        return new ArticleResponseDto(article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getTag(),
                article.getCreatedAt(),
                article.getModifiedAt(),
                article.getMember().getName());
    }
}
