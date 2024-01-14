package com.spring.chatserver.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
    private String username;
}
