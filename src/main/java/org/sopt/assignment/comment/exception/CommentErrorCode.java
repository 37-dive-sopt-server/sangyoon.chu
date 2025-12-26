package org.sopt.assignment.comment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.assignment.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "COMMENT_001", "해당 댓글을 찾을 수 없습니다."),
    NOT_MATCH_ARTICLE(HttpStatus.NOT_FOUND, "COMMENT_002", "해당 댓글은 아티클과 일치하지 않습니다."),
    NOT_MATCH_MEMBER(HttpStatus.FORBIDDEN, "COMMENT_003", "댓글은 작성자만 수정 가능합니다.");



    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
