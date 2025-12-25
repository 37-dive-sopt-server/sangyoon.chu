package org.sopt.assignment.comment.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.comment.domain.Comment;
import org.sopt.assignment.comment.dto.command.CreateCommentCommandDto;
import org.sopt.assignment.comment.dto.request.CreateCommentRequestDto;
import org.sopt.assignment.comment.dto.response.GetCommentResponseDto;
import org.sopt.assignment.comment.service.CommentService;
import org.sopt.assignment.global.annotation.LoginUser;
import org.sopt.assignment.global.dto.PageBaseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles/{articleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void createComment(@PathVariable Long articleId,
                              @RequestBody CreateCommentRequestDto request,
                              @LoginUser Long memberId) {
        commentService.createComment(CreateCommentCommandDto.of(request, memberId, articleId));
    }

    @GetMapping
    public PageBaseDto<GetCommentResponseDto> getComment(@PathVariable Long articleId,
                                                         @RequestParam(defaultValue = "0") int page){

        Pageable pageable = PageRequest.of(page , 10);

        return commentService.getComments(articleId, pageable);
    }
}
