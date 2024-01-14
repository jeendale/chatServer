package com.spring.chatserver.domain.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatReq {

    private Long roomId;
    private Long senderId;
    private String content;

}
