package com.spring.chatserver.domain.user.dto.request;

import lombok.Getter;

@Getter
public class SignUpReq {
    private String username;
    private String password;
}
