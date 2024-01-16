package com.spring.chatserver.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomRes {

    private Long roomId;

    @Builder
    private RoomRes(Long roomId){
        this.roomId=roomId;
    }
}
