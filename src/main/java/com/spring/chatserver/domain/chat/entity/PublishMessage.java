package com.spring.chatserver.domain.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PublishMessage implements Serializable {

    @NotNull
    private Long roomId;
    @NotNull
    private Long senderId;
    private String content;

    private LocalDateTime createdAt;

}