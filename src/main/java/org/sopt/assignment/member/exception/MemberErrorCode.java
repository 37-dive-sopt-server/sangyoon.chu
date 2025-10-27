package org.sopt.assignment.member.exception;

import org.sopt.assignment.global.util.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode {
    NOT_EMPTY_NAME(HttpStatus.BAD_REQUEST, "MEMBER_001", "⚠️ 이름은 필수 입력값입니다."),
    NOT_EMPTY_EMAIL(HttpStatus.BAD_REQUEST, "MEMBER_002", "⚠️ 이메일은 필수 입력값입니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST,"MEMBER_003", "⚠️ 올바른 이메일 형식이 아닙니다."),
    NOT_EMPTY_BIRTHDAY(HttpStatus.BAD_REQUEST,"MEMBER_004", "⚠️ 생년월일은 필수 입력값입니다."),
    NOT_ALLOWED_FUTURE_BIRTHDAY(HttpStatus.BAD_REQUEST,"MEMBER_005", "⚠️ 생년월일이 미래일 수 없습니다."),
    NOT_ALLOWED_AGE_UNDER_TWENTY(HttpStatus.BAD_REQUEST,"MEMBER_006", "⚠️ 만 20세 미만은 가입할 수 없습니다."),
    INVALID_GENDER(HttpStatus.BAD_REQUEST,"MEMBER_007", "⚠️ 성별은 MALE 또는 FEMALE로만 입력해주세요."),
    NOT_DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST,"MEMBER_008", "⚠️ 입력된 이메일은 이미 등록된 이메일입니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND,"MEMBER_009", "⚠️ 해당 ID의 회원을 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    MemberErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus(){
        return this.status;
    }

    public String getErrorCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
