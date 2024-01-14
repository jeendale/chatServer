package com.spring.chatserver.domain.chat.repository;

import com.spring.chatserver.domain.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long> {
}
