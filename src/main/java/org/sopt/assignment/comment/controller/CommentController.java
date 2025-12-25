package org.sopt.assignment.comment.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.comment.dto.command.CreateCommentCommandDto;
import org.sopt.assignment.comment.dto.command.UpdateCommentCommandDto;
import org.sopt.assignment.comment.dto.request.CreateCommentRequestDto;
import org.sopt.assignment.comment.dto.request.UpdateCommentRequestDto;
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

    @GetMapping("/v1")
    public PageBaseDto<GetCommentResponseDto> getCommentV1(@PathVariable Long articleId,
                                                         @RequestParam(defaultValue = "0") int page){

        Pageable pageable = PageRequest.of(page , 10);

        return commentService.getCommentsV1(articleId, pageable);
    }


    @GetMapping("/v2")
    public PageBaseDto<GetCommentResponseDto> getCommentV2(@PathVariable Long articleId,
                                                         @RequestParam(defaultValue = "0") int page){

        Pageable pageable = PageRequest.of(page , 10);

        return commentService.getCommentsV2(articleId, pageable);
    }

    @GetMapping("/v3")
    public PageBaseDto<GetCommentResponseDto> getCommentV3(@PathVariable Long articleId,
                                                           @RequestParam(defaultValue = "0") int page){

        Pageable pageable = PageRequest.of(page , 10);

        return commentService.getCommentsV3(articleId, pageable);
    }

    @GetMapping("/v4")
    public PageBaseDto<GetCommentResponseDto> getCommentV4(@PathVariable Long articleId,
                                                           @RequestParam(defaultValue = "0") int page){

        Pageable pageable = PageRequest.of(page , 10);

        return commentService.getCommentsV4(articleId, pageable);
    }

    @GetMapping("/v5")
    public PageBaseDto<GetCommentResponseDto> getCommentV5(@PathVariable Long articleId,
                                                           @RequestParam(defaultValue = "0") int page){

        Pageable pageable = PageRequest.of(page , 10);

        return commentService.getCommentsV5(articleId, pageable);
    }

    @PatchMapping("/{commentId}")
    public void updateComment(@PathVariable Long articleId,
                              @PathVariable Long commentId,
                              @RequestBody UpdateCommentRequestDto request,
                              @LoginUser Long memberId){
        commentService.updateComment(UpdateCommentCommandDto.of(articleId, commentId, request, memberId));
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long articleId,
                              @PathVariable Long commentId,
                              @LoginUser Long memberId){
        commentService.deleteComment(articleId, commentId, memberId);
    }
}
