package org.sopt.assignment.global.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.sopt.assignment.global.dto.CustomErrorResponse;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.global.exception.CommonErrorCode;
import org.sopt.assignment.global.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


     //커스텀 예외 처리 (BaseException 및 하위 클래스)
     // 비즈니스 로직에서 발생하는 모든 예외 처리
     @ExceptionHandler(BaseException.class)
     public ResponseEntity<CustomErrorResponse> handleBaseException(BaseException e) {
        ErrorCode code = e.getErrorCode();
        logError(code, e);

        return convert(code);
    }



    // 요청 데이터 Validation 전용 ExceptionHandler (@ModelAttribute)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<CustomErrorResponse> bindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return convert(extractErrorMessage(fieldErrors));
    }

    private String extractErrorMessage(List<FieldError> fieldErrors) {
        if (fieldErrors.size() == 1) {
            return fieldErrors.getFirst().getDefaultMessage();
        }

        StringBuilder buffer = new StringBuilder();
        for (FieldError error : fieldErrors) {
            buffer.append(error.getDefaultMessage()).append("\n");
        }
        return buffer.toString();
    }



     //존재하지 않는 엔드포인트 또는 타입 변환 실패 처리
    @ExceptionHandler({NoHandlerFoundException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<CustomErrorResponse> handleNotFoundOrTypeMismatch(Exception e) {
        if (e instanceof MethodArgumentTypeMismatchException typeMismatch) {
           // log.warn("Type conversion failed: {} -> {}",
            //        typeMismatch.getValue(), typeMismatch.getRequiredType().getSimpleName());
        } else {
           // log.warn("Handler not found: {}", e.getMessage());
        }

        return convert(CommonErrorCode.NOT_SUPPORTED_URI_ERROR);
    }

    //지원하지 않는 HTTP 메서드 처리 (405)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        // log.warn("Method not supported: {} for {}", e.getMethod(), e.getMessage());
        return convert(CommonErrorCode.NOT_SUPPORTED_METHOD_ERROR);
    }

     //지원하지 않는 미디어 타입 처리 (415)
     @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<CustomErrorResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException e,
                                                                           HttpServletRequest request) {
        return convert(CommonErrorCode.NOT_SUPPORTED_MEDIA_TYPE_ERROR);
    }

     // JSON 파싱 실패 처리 (400)
     @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        // log.warn("JSON 파싱 실패: {}", e.getMessage());
        return convert("요청 형식이 잘못되었습니다. 입력값을 확인해주세요.");
    }

    // 위의 핸들러들로 처리되지 않은 모든 RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponse> handleUnexpectedException(RuntimeException e, HttpServletRequest request) {
        // log.error("Unexpected error occurred", e);
        // log.error("Request info: {} {}", request.getMethod(), request.getRequestURI());

        return convert(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<CustomErrorResponse> convert(ErrorCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(CustomErrorResponse.from(code));
    }


    private ResponseEntity<CustomErrorResponse> convert(String message) {
        return ResponseEntity
                .status(CommonErrorCode.VALIDATION_ERROR.getStatus())
                .body(CustomErrorResponse.of(CommonErrorCode.VALIDATION_ERROR, message));
    }

    private void logError(ErrorCode code, Exception e) {
        /*log.warn("[{}] {} | {} | {} | Message: {}",
                "BaseException",
                code.getStatus().value(),
                code.getErrorCode(),
                code.getMessage(),
                e.getMessage());
           */
    }

}