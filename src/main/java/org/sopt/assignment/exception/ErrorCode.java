package org.sopt.assignment.exception;

public enum ErrorCode {
    NOT_EMPTY_NAME("ERROR1", "⚠️ 이름은 필수 입력값입니다."),
    NOT_EMPTY_EMAIL("ERROR2", "⚠️ 이메일은 필수 입력값입니다."),
    INVALID_EMAIL("ERROR3", "⚠️ 올바른 이메일 형식이 아닙니다."),
    NOT_EMPTY_BIRTHDAY("ERROR4", "⚠️ 생년월일은 필수 입력값입니다."),
    NOT_ALLOWED_FUTURE_BIRTHDAY("ERROR5", "⚠️ 생년월일이 미래일 수 없습니다."),
    NOT_ALLOWED_AGE_UNDER_TWENTY("ERROR6", "⚠️ 만 20세 미만은 가입할 수 없습니다."),
    INVALID_GENDER("ERROR7", "⚠️ 성별은 1(남성) 또는 2(여성)로만 입력해주세요."),
    NOT_DUPLICATED_EMAIL("ERROR8", "⚠️ 입력된 이메일은 이미 등록된 이메일입니다."),
    NOT_FOUND_MEMBER("ERROR9", "⚠️ 해당 ID의 회원을 찾을 수 없습니다."),
    INVALID_MENU_NUMBER("ERROR10", "🚫 잘못된 메뉴 선택입니다. 다시 시도해주세요.")
    ;

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
