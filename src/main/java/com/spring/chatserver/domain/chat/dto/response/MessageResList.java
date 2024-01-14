package com.spring.chatserver.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageResList {

    private List<MessageRes> messageReses;

    @Builder
    private MessageResList(List<MessageRes> messageRes){
        this.messageReses=messageRes;
    }

}
