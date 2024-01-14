package com.spring.chatserver.domain.chat.service;

import com.spring.chatserver.domain.chat.dto.request.ChatReq;
import com.spring.chatserver.domain.chat.entity.Chat;
import com.spring.chatserver.domain.chat.entity.ChatRoom;
import com.spring.chatserver.domain.chat.repository.ChatRepository;
import com.spring.chatserver.domain.chat.repository.ChatRoomRepository;
import com.spring.chatserver.domain.user.entity.User;
import com.spring.chatserver.domain.user.repository.UserRepository;
import com.spring.chatserver.domain.user.service.UserService;
import com.spring.chatserver.global.exception.GlobalException;
import com.spring.chatserver.global.validate.UserValidator;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.chatserver.global.meta.ResultCode.NOT_FOUND_FILE;

@Service
@Slf4j
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


        //chatRepository에 저장은..?->스케줄러로 1시간마다 해준다
    }
    @Scheduled(cron = "0 0 0/1 * * *") // 한시간마다
    @Transactional
    public void saveMessages() {
        // 레디스에 캐싱된 채팅방 아이디만 파싱
        List<Long> roomIdList = redisTemplate.keys(MESSAGE_CACHE_KEY+"*").stream()
                .map(key -> Long.parseLong(key.substring(MESSAGE_CACHE_KEY.length())))
                .collect(Collectors.toList());
        // 각 채팅방의 캐싱된 메세지를 찾아 DB에 저장한 후, 캐싱된 메세지는 삭제
        for(Long id : roomIdList) {
            String cacheKey = MESSAGE_CACHE_KEY + id;
            try{
                List<Chat> messages = redisTemplate.opsForList().range(cacheKey, 0, -1);
                if(messages != null && messages.size() > 0) {
                    chatRepository.saveAll(messages);
                    redisTemplate.opsForList().trim(cacheKey, messages.size(), -1);
                } else {
                    continue;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new GlobalException(NOT_FOUND_FILE); //맞나..?데이터가 없느것
            }
        }
    }


}
