package com.spring.chatserver.domain.chat.controller;

import com.spring.chatserver.domain.chat.dto.request.ChatReq;
import com.spring.chatserver.domain.chat.entity.PublishMessage;
import com.spring.chatserver.domain.chat.service.ChatService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ChatController {
    //private SimpMessagingTemplate template;
    private ChatService chatService;

    private final ChannelTopic topic;

    @Resource(name = "chatRedisTemplate")
    private final RedisTemplate redisTemplate;

    //pub/chats/meessage/{room-id}으로 맵핑
    @MessageMapping("/chats/message/{room-id}")
    public void message(@DestinationVariable("room-id")Long roomId, ChatReq chatReq){
        PublishMessage publishMessage
                = new PublishMessage(chatReq.getRoomId(), chatReq.getSenderId(), chatReq.getContent(), LocalDateTime.now());

        //채팅방에 채팅전송
        redisTemplate.convertAndSend(topic.getTopic(),publishMessage);
        //메세지 캐시처리
        chatService.message(chatReq,roomId);
    }


}
