package org.sopt.assignment.comment.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.comment.dto.command.CreateCommentCommandDto;
import org.sopt.assignment.comment.dto.request.CreateCommentRequestDto;
import org.sopt.assignment.comment.service.CommentService;
import org.sopt.assignment.global.annotation.LoginUser;
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

}
