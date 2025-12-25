package org.sopt.assignment.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.article.domain.Article;
import org.sopt.assignment.article.service.ArticleService;
import org.sopt.assignment.comment.domain.Comment;
import org.sopt.assignment.comment.dto.command.CreateCommentCommandDto;
import org.sopt.assignment.comment.dto.command.UpdateCommentCommandDto;
import org.sopt.assignment.comment.dto.response.GetCommentResponseDto;
import org.sopt.assignment.comment.exception.CommentErrorCode;
import org.sopt.assignment.comment.repository.CommentRepository;
import org.sopt.assignment.global.dto.PageBaseDto;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.member.domain.Member;
import org.sopt.assignment.member.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    private final MemberService memberService;

    @Transactional
    public void createComment(CreateCommentCommandDto command){

        Member member = memberService.getMemberById(command.memberId());

        Article article = articleService.get(command.articleId());

        commentRepository.save(Comment.create(command.comment(), member, article));
    }

    @Transactional(readOnly = true)
    public PageBaseDto<GetCommentResponseDto> getComments(Long articleId, Pageable pageable) {

        articleService.validateArticleExists(articleId);

        return PageBaseDto.from(commentRepository
                .findByArticleId(articleId, pageable).map(GetCommentResponseDto::from));
    }

    @Transactional
    public void updateComment(UpdateCommentCommandDto command){

        Comment comment = getMyComment(command.commentId(), command.articleId(), command.memberId());

        comment.update(command.content());
    }

    @Transactional
    public void deleteComment(Long articleId, Long commentId, Long memberId) {
        Comment comment = getMyComment(commentId, articleId, memberId);

        commentRepository.delete(comment);
    }

    private Comment getMyComment(Long commentId, Long articleId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> BaseException.type(CommentErrorCode.NOT_FOUND_COMMENT));

        if(!comment.belongsToArticle(articleId)) {
            throw BaseException.type(CommentErrorCode.NOT_MATCH_ARTICLE);
        }

        if(!comment.isOwnedBy(memberId)) {
            throw BaseException.type(CommentErrorCode.NOT_MATCH_MEMBER);
        }

        return comment;
    }
}
