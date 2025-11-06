package org.sopt.assignment.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.assignment.article.dto.command.SaveArticleCommandDto;
import org.sopt.assignment.article.dto.request.SaveArticleRequestDto;
import org.sopt.assignment.article.dto.response.ArticleResponseDto;
import org.sopt.assignment.article.dto.response.GetListArticleResponseDto;
import org.sopt.assignment.article.service.ArticleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ArticleResponseDto saveArticle(
            @RequestBody @Valid SaveArticleRequestDto request,
            @PathVariable Long memberId){

        return articleService.saveArticle(SaveArticleCommandDto.of(request, memberId));
    }

    @GetMapping
    public GetListArticleResponseDto getAllArticle(
            @PathVariable Long memberId){
        return articleService.getListArticle(memberId);
    }

    @GetMapping("/{articleId}")
    public ArticleResponseDto getArticle(
            @PathVariable Long articleId,
            @PathVariable Long memberId
    ){
        return articleService.getArticle(articleId, memberId);
    }
}
