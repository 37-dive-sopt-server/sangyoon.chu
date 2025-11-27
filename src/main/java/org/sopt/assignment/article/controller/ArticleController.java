package org.sopt.assignment.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.assignment.article.domain.ESearchType;
import org.sopt.assignment.article.domain.ETag;
import org.sopt.assignment.article.dto.command.SaveArticleCommandDto;
import org.sopt.assignment.article.dto.request.SaveArticleRequestDto;
import org.sopt.assignment.article.dto.response.ArticleResponseDto;
import org.sopt.assignment.article.dto.response.GetListArticleResponseDto;
import org.sopt.assignment.article.service.ArticleService;
import org.sopt.assignment.global.annotation.LoginUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("articles")
    public ArticleResponseDto saveArticle(
            @RequestBody @Valid SaveArticleRequestDto request,
            @LoginUser Long memberId){

        return articleService.saveArticle(SaveArticleCommandDto.of(request, memberId));
    }

    @GetMapping("articles")
    public GetListArticleResponseDto getAllArticle(
            @RequestParam (defaultValue = "0")Integer page){

        Pageable pageable = PageRequest.of(page, 10);

        return articleService.getListArticle(pageable);
    }

    @GetMapping("/articles/{articleId}")
    public ArticleResponseDto getArticle(
            @PathVariable Long articleId
    ){
        return articleService.getArticle(articleId);
    }

    @GetMapping("/articles/search")
    public GetListArticleResponseDto searchArticle(
            @RequestParam ESearchType searchType,
            @RequestParam String searchKeyword,
            @RequestParam(defaultValue = "0") Integer page){

            String keyword = searchKeyword.trim();

            Pageable pageable = PageRequest.of(page, 10);

        return articleService.searchArticle(searchType, keyword, pageable);
    }

}
