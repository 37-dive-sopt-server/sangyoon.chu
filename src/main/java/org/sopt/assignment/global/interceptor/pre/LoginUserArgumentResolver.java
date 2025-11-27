package org.sopt.assignment.global.interceptor.pre;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.global.annotation.LoginUser;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.global.exception.CommonErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class)
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Object userId = webRequest.getAttribute("USER_ID", NativeWebRequest.SCOPE_REQUEST);
        if(userId == null) {
            throw BaseException.type(CommonErrorCode.INVALID_HEADER_VALUE);
        }
        return userId;
        }
}
