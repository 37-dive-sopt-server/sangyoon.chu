package org.sopt.assignment.article.dto.response;

import org.sopt.assignment.global.dto.PageBaseDto;
import org.springframework.data.domain.Page;

public record GetListArticleResponseDto(
        PageBaseDto<GetListArticleResponse> articleResponseDtoList
) {
    public static GetListArticleResponseDto of(Page<GetListArticleResponse> articleList){
        return new GetListArticleResponseDto(PageBaseDto.from(articleList));
    }
}
