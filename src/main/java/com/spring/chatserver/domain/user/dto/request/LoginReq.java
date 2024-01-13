package com.spring.chatserver.domain.user.dto.request;

import lombok.Getter;

@Getter
public class LoginReq {
    private String username;
    private String password;
}
