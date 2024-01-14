package com.spring.chatserver.domain.chat.controller;

import com.spring.chatserver.domain.chat.dto.request.ChatReq;
import com.spring.chatserver.domain.chat.dto.response.ChatRes;
import com.spring.chatserver.domain.chat.dto.response.MessageRes;
import com.spring.chatserver.domain.chat.dto.response.MessageResList;
import com.spring.chatserver.domain.chat.entity.PublishMessage;
import com.spring.chatserver.domain.chat.service.ChatService;
import com.spring.chatserver.domain.user.security.UserDetailsImpl;
import com.spring.chatserver.global.exception.GlobalException;
import com.spring.chatserver.global.meta.ResultCode;
import com.spring.chatserver.global.response.RestResponse;
import com.spring.chatserver.global.validate.UserValidator;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    //private SimpMessagingTemplate template;
    private ChatService chatService;

    private final ChannelTopic topic;

    @Resource(name = "chatRedisTemplate")
    private final RedisTemplate redisTemplate;

    //pub/chats/meessage/{room-id}으로 맵핑
    @MessageMapping("/chats/message/{room-id}")
    public RestResponse<ChatRes> message(@DestinationVariable("room-id")Long roomId, ChatReq chatReq){
        ChatRes chatRes=new ChatRes();
        PublishMessage publishMessage
                = new PublishMessage(chatReq.getRoomId(), chatReq.getSenderId(), chatReq.getContent(), LocalDateTime.now());

        //채팅방에 채팅전송
        redisTemplate.convertAndSend(topic.getTopic(),publishMessage);
        //메세지 캐시처리
        chatService.message(chatReq,roomId);

        return RestResponse.success(chatRes);
    }
    // 채팅메세지 가져오기
 /*   @GetMapping("/chats/messages/{room-id}")
    public RestResponse<MessageResList> getMessages(@PathVariable("room-id") long roomId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {

        UserValidator.validate(userDetails.getUser());
        re
    }*/

}
