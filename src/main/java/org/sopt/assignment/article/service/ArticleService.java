package org.sopt.assignment.article.service;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.article.domain.Article;
import org.sopt.assignment.article.dto.command.SaveArticleCommandDto;
import org.sopt.assignment.article.dto.response.ArticleResponseDto;
import org.sopt.assignment.article.dto.response.GetListArticleResponse;
import org.sopt.assignment.article.dto.response.GetListArticleResponseDto;
import org.sopt.assignment.article.exception.ArticleErrorCode;
import org.sopt.assignment.article.repository.ArticleRepository;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.member.domain.Member;
import org.sopt.assignment.member.service.MemberService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    public ArticleResponseDto saveArticle(SaveArticleCommandDto command){
            Member member = memberService.getMemberById(command.memberId());

            validateDuplicateTitle(command.title());

            Article article =articleRepository.save(Article.create(command.title(),
                    command.content(),
                    command.tag(),
                    member));

        return ArticleResponseDto.from(article);
    }

    public GetListArticleResponseDto getListArticle(Long memberId){
        return GetListArticleResponseDto.of(
                articleRepository.findAllByMemberId(memberId)
                    .stream()
                        .map(GetListArticleResponse::from)
                        .toList());

    }

    public ArticleResponseDto getArticle(Long articleId, Long memberId){
        Article article = articleRepository.findByIdAndMemberId(articleId, memberId)
                .orElseThrow(()-> BaseException.type(ArticleErrorCode.NOT_FOUND_ARTICLE));

        return ArticleResponseDto.from(article);
    }

    private void validateDuplicateTitle(String title){
        if(articleRepository.existsByTitle(title)){
            throw BaseException.type(ArticleErrorCode.ALREADY_USED_ARTICLE_TITLE);
        }
    }
}
