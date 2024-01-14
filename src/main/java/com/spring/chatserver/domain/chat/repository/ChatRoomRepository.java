package com.spring.chatserver.domain.chat.repository;

import com.spring.chatserver.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    ChatRoom findByRoomId(Long roomId);
}
