package com.spring.chatserver.domain.chat.service;

import com.spring.chatserver.domain.chat.dto.response.RoomRes;
import com.spring.chatserver.domain.chat.entity.ChatRoom;
import com.spring.chatserver.domain.chat.repository.ChatRoomRepository;
import com.spring.chatserver.domain.user.entity.User;
import com.spring.chatserver.domain.user.repository.UserRepository;
import com.spring.chatserver.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final ChatRoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomRes createRoom(String recieverName, UserDetailsImpl userDetails){

        User receiver =userRepository.findByUsername(recieverName);
        User sender=userRepository.findByUsername(userDetails.getUsername());

        //둘의 채팅 유무 확인
        //user로 파라미터 값을 받아오면 왜 오류가 생길까?
        Optional<ChatRoom> useChatRoom =roomRepository.findBySenderAndReceiver(sender,receiver);
        Optional<ChatRoom> useChatRoom2 =roomRepository.findBySenderAndReceiver(sender,receiver);

        ChatRoom chatRoom=null;
        RoomRes roomRes=null;
        if(useChatRoom.isPresent()){
            chatRoom=useChatRoom.get();
            roomRes= RoomRes.builder().roomId(chatRoom.getRoomId()).build();
            return roomRes;
        }else if(useChatRoom2.isPresent()){
            chatRoom=useChatRoom2.get();
            roomRes= RoomRes.builder().roomId(chatRoom.getRoomId()).build();
            return roomRes;
        }else{
            chatRoom=chatRoom.builder().sender(sender).receiver(receiver).build();
        }

        ChatRoom createChatroom=roomRepository.save(chatRoom);
        roomRes=RoomRes.builder().roomId(createChatroom.getRoomId()).build();
        return roomRes;
    }

}
