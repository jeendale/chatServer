package com.spring.chatserver.domain.chat.service;

import com.spring.chatserver.domain.chat.dto.request.ChatReq;
import com.spring.chatserver.domain.chat.entity.Chat;
import com.spring.chatserver.domain.chat.entity.ChatRoom;
import com.spring.chatserver.domain.chat.repository.ChatRepository;
import com.spring.chatserver.domain.chat.repository.ChatRoomRepository;
import com.spring.chatserver.domain.user.entity.User;
import com.spring.chatserver.domain.user.repository.UserRepository;
import com.spring.chatserver.domain.user.service.UserService;
import com.spring.chatserver.global.validate.UserValidator;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @Resource(name = "chatRedisTemplate")
    private final RedisTemplate<String, Chat> redisTemplate;

    private static final String MESSAGE_CACHE_KEY = "CacheChatRoom:";
    //chace메세지
    public void message(ChatReq chatReq,Long roomId){
        User user=userRepository.findBySenderId(chatReq.getSenderId());
        ChatRoom chatRoom=chatRoomRepository.findByRoomId(roomId);

        UserValidator.validate(user);
        Chat chat= Chat.builder()
                .content(chatReq.getContent())
                .sender(user)
                .sendTime(LocalDateTime.now())
                .chatRoom(chatRoom)
                .build();

        String cacheKey = MESSAGE_CACHE_KEY+roomId;

        redisTemplate.opsForList().rightPush(cacheKey, chat);

    }

}
