package com.spring.chatserver.domain.chat.service;

import jakarta.persistence.ManyToOne;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatServiceMapper {

    ChatServiceMapper INSTANCE= Mappers.getMapper(ChatServiceMapper.class);

}
