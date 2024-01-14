package com.spring.chatserver.domain.chat.dto.response;

import com.spring.chatserver.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRes {
    private User senderId;
    private User receiverID;
    private String content;

    @Builder
    private MessageRes(User senderId,User receiverID,String content){
        this.senderId=senderId;
        this.receiverID=receiverID;
        this.content=content;
    }
}
