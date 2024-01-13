package com.spring.chatserver.domain.user.controller;

import com.spring.chatserver.domain.user.dto.request.LoginReq;
import com.spring.chatserver.domain.user.dto.request.SignUpReq;
import com.spring.chatserver.domain.user.dto.response.LoginRes;
import com.spring.chatserver.domain.user.dto.response.SignUpRes;
import com.spring.chatserver.domain.user.service.UserService;
import com.spring.chatserver.global.response.RestResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public RestResponse<SignUpRes> signUp(@RequestBody SignUpReq signUpReq) {
        return RestResponse.success(userService.signUp(signUpReq));
    }

    @GetMapping("/login")
    public RestResponse<LoginRes> login(
            @RequestBody LoginReq loginReq, HttpServletResponse response) {
        return RestResponse.success(userService.login(loginReq, response));
    }
}
