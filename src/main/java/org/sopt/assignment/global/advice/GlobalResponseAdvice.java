package org.sopt.assignment.global.advice;

import org.sopt.assignment.global.dto.CommonApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> parameterType = returnType.getParameterType();
        // 제외할 타입들
        return !parameterType.equals(CommonApiResponse.class) &&
                !ResponseEntity.class.isAssignableFrom(parameterType) &&
                !parameterType.equals(String.class) &&
                !parameterType.getName().startsWith("org.springframework") &&
                !parameterType.getName().startsWith("springfox") &&
                !parameterType.getName().startsWith("io.swagger");
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {


        String path = request.getURI().getPath();

       if (path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger") ||
                path.startsWith("/error") ||
                path.startsWith("/actuator") ||
                path.contains("api-docs")) {

            return body;
        }

        return CommonApiResponse.success(body);
    }
}
