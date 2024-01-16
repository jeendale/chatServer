package com.spring.chatserver.domain.chat.controller;

import com.spring.chatserver.domain.chat.dto.request.RoomReq;
import com.spring.chatserver.domain.chat.dto.response.RoomRes;
import com.spring.chatserver.domain.chat.service.ChatServiceMapper;
import com.spring.chatserver.domain.chat.service.RoomService;
import com.spring.chatserver.domain.user.entity.User;
import com.spring.chatserver.domain.user.repository.UserRepository;
import com.spring.chatserver.domain.user.security.UserDetailsImpl;
import com.spring.chatserver.global.response.RestResponse;
import com.spring.chatserver.global.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/rooms")
@Slf4j
@RequiredArgsConstructor
public class RoomController {
    private RoomService roomService;
    private UserRepository userRepository;
    @PostMapping
    public RestResponse<RoomRes> CreateRoom(@RequestBody RoomReq roomReq
                                            ,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        //보내는 사람 확인
        UserValidator.validate(userDetails.getUser());

        //받는 사람 확인
        User receiver = userRepository.findByUsername((ChatServiceMapper.INSTANCE.toRoomReqUser(roomReq)).getUsername());
        UserValidator.validate(receiver);

        return RestResponse.success(roomService.createRoom(receiver.getUsername(), userDetails));

    }

}
