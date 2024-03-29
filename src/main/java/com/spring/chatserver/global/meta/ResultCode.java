package com.spring.chatserver.global.meta;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(0, "정상 처리 되었습니다"),

    SYSTEM_ERROR(1000, "알 수 없는 에러가 발생했습니다."),
    NOT_FOUND_FILE(1001, "파일을 찾을 수 없습니다."),

    NOT_FOUND_SAMPLE(2002, "샘플 데이터가 없습니다."),

    // 이메일 유효성 검사
    INVALID_EMAIL(1002, "올바른 이메일 주소가 아닙니다. 다시 입력해주세요"),
    EMPTY_EMAIL(1003, "이메일을 입력해주세요"),
    EMAIL_SEND_ERROR(2000, "이메일 전송 중 오류가 발생했습니다."),
    NOT_FOUND_USER(3000, "유저 정보가 없습니다."),
    PASSWORD_MISMATCH(3001, "비밀번호가 일치하지 않습니다."),

    NOT_FOUND_POST(4002, "모집글 데이터가 없습니다.");
    private Integer code;
    private String message;

    ResultCode(Integer code, String errorMessage) {
        this.code = code;
        this.message = errorMessage;
    }
}
