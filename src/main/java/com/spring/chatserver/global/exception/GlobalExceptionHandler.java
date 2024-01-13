package com.spring.chatserver.global.exception;

import com.spring.chatserver.global.response.RestResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public RestResponse<Void> handleException(GlobalException e) {
        return RestResponse.error(e.getResultCode());
    }
}
