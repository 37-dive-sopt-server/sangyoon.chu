package org.sopt.assignment.article.dto.command;

import org.sopt.assignment.article.domain.ETag;
import org.sopt.assignment.article.dto.request.SaveArticleRequestDto;

public record SaveArticleCommandDto(

        String title,

        String content,

        ETag tag,

        Long memberId
) {
    public static SaveArticleCommandDto of(SaveArticleRequestDto request, Long memberId){
        return new SaveArticleCommandDto(request.title(),
                request.content(),
                request.tag(),
                memberId);
    }
}
