package com.spring.chatserver.global.exception;

import com.spring.chatserver.global.meta.ResultCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ResultCode resultCode;

    public GlobalException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
