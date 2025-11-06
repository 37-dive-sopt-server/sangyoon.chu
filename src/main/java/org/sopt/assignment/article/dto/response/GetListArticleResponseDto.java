package org.sopt.assignment.article.dto.response;

import java.util.List;

public record GetListArticleResponseDto(
        List<GetListArticleResponse> articleResponseDtoList
) {
    public static GetListArticleResponseDto of(List<GetListArticleResponse> articleList){
        return new GetListArticleResponseDto(articleList);
    }
}
