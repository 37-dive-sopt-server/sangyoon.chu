package org.sopt.assignment.article.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.assignment.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ArticleErrorCode implements ErrorCode {
    NOT_EMPTY_TITLE(HttpStatus.BAD_REQUEST, "ARTICLE_001", "제목은 필수 입력값입니다."),
    NOT_EMPTY_CONTENT(HttpStatus.BAD_REQUEST, "ARTICLE_002", "내용은 필수 입력값입니다."),
    NOT_EMPTY_TAG(HttpStatus.BAD_REQUEST, "ARTICLE_003", "태그는 필수 입력값 입니다.");


    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
