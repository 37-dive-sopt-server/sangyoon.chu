package org.sopt.assignment.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.article.domain.Article;
import org.sopt.assignment.article.service.ArticleService;
import org.sopt.assignment.comment.domain.Comment;
import org.sopt.assignment.comment.dto.command.CreateCommentCommandDto;
import org.sopt.assignment.comment.dto.response.GetCommentResponseDto;
import org.sopt.assignment.comment.repository.CommentRepository;
import org.sopt.assignment.global.dto.PageBaseDto;
import org.sopt.assignment.member.domain.Member;
import org.sopt.assignment.member.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    private final MemberService memberService;

    public void createComment(CreateCommentCommandDto command){

        Member member = memberService.getMemberById(command.memberId());

        Article article = articleService.get(command.articleId());

        Comment.create(command.comment(), member, article);
    }

    public PageBaseDto<GetCommentResponseDto> getComments(Long articleId, Pageable pageable) {

        articleService.validateArticleExists(articleId);

        return PageBaseDto.from(commentRepository
                .findByArticleId(articleId, pageable).map(GetCommentResponseDto::from));
    }


}
