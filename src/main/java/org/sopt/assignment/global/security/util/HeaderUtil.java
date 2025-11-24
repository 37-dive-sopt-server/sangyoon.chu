package org.sopt.assignment.global.security.util;

import jakarta.servlet.http.HttpServletRequest;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.global.exception.CommonErrorCode;
import org.springframework.util.StringUtils;

public class HeaderUtil {
    public static String refineHeader(
            HttpServletRequest request,
            String headerName,
            String prefix
    ){
        String headerValue = request.getHeader(headerName);
        if(!StringUtils.hasText(headerValue) || !headerValue.startsWith(prefix))
                throw BaseException.type(CommonErrorCode.INVALID_HEADER_VALUE);

        return headerValue.substring(prefix.length());
    }
}