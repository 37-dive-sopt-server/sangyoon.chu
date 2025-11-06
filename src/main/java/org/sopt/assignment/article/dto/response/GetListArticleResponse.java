package org.sopt.assignment.article.dto.response;

import org.sopt.assignment.article.domain.Article;
import org.sopt.assignment.article.domain.ETag;

import java.time.LocalDateTime;

public record GetListArticleResponse(
        Long id,

        String title,

        ETag tag,

        LocalDateTime createdAt

) {
    public static GetListArticleResponse from(Article article){
         return new GetListArticleResponse(article.getId(),
                article.getTitle(),
                article.getTag(),
                article.getCreatedAt());
    }
}
