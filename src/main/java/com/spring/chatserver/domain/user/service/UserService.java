package com.spring.chatserver.domain.user.service;

import com.spring.chatserver.domain.user.dto.request.LoginReq;
import com.spring.chatserver.domain.user.dto.request.SignUpReq;
import com.spring.chatserver.domain.user.dto.response.LoginRes;
import com.spring.chatserver.domain.user.dto.response.SignUpRes;
import com.spring.chatserver.domain.user.entity.User;
import com.spring.chatserver.domain.user.repository.UserRepository;
import com.spring.chatserver.domain.user.util.JwtUtil;
import com.spring.chatserver.global.exception.GlobalException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public SignUpRes signUp(SignUpReq signUpReq) {
        String encodedPassword = passwordEncoder.encode(signUpReq.getPassword());
        User user = new User(signUpReq, encodedPassword);
        userRepository.save(user);

        return new SignUpRes();
    }

    public LoginRes login(LoginReq loginReq, HttpServletResponse response) {
        User user = userRepository.findByUsername(loginReq.getUsername());
        // 비밀번호 일치
        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // user.getRole() 사용위해 JWT생성 및 쿠키에 저장
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginReq.getUsername()));

        return new LoginRes();
    }


}
