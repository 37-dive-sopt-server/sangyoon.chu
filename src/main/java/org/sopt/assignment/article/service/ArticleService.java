package org.sopt.assignment.article.service;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.article.domain.Article;
import org.sopt.assignment.article.domain.ESearchType;
import org.sopt.assignment.article.dto.command.SaveArticleCommandDto;
import org.sopt.assignment.article.dto.response.ArticleResponseDto;
import org.sopt.assignment.article.dto.response.GetListArticleResponse;
import org.sopt.assignment.article.dto.response.GetListArticleResponseDto;
import org.sopt.assignment.article.exception.ArticleErrorCode;
import org.sopt.assignment.article.repository.ArticleRepository;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.member.domain.Member;
import org.sopt.assignment.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    @Transactional
    public ArticleResponseDto saveArticle(SaveArticleCommandDto command){
            Member member = memberService.getMemberById(command.memberId());

            validateDuplicateTitle(command.title());

            Article article =articleRepository.save(Article.create(command.title(),
                    command.content(),
                    command.tag(),
                    member));

        return ArticleResponseDto.of(article);
    }

    @Transactional(readOnly = true)
    public GetListArticleResponseDto getListArticle(Pageable pageable){
        return GetListArticleResponseDto.of(
                articleRepository.findAllWithMember(pageable)
                        .map(GetListArticleResponse::from));

    }

    @Transactional(readOnly = true)
    public ArticleResponseDto getArticle(Long articleId){
        Article article = articleRepository.findByIdWithMember(articleId)
                .orElseThrow(()-> BaseException.type(ArticleErrorCode.NOT_FOUND_ARTICLE));
        return ArticleResponseDto.of(article);
    }

    @Transactional(readOnly = true)
    public GetListArticleResponseDto searchArticle(ESearchType searchType, String keyword, Pageable pageable){
        Page<Article> articlePage = switch(searchType){
            case TITLE -> articleRepository.findByTitleContainingIgnoreCaseWithMember(keyword,pageable);
            case NAME -> articleRepository.findByMemberNameContainingIgnoreCaseWithMember(keyword,pageable);
        };

        return GetListArticleResponseDto.of(articlePage.map(GetListArticleResponse::from));
    }

    private void validateDuplicateTitle(String title){
        if(articleRepository.existsByTitle(title)){
            throw BaseException.type(ArticleErrorCode.ALREADY_USED_ARTICLE_TITLE);
        }
    }
}
