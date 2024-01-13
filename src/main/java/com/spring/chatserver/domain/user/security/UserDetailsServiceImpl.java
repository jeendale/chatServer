package com.spring.chatserver.domain.user.security;

import com.spring.chatserver.domain.user.entity.User;
import com.spring.chatserver.domain.user.repository.UserRepository;
import com.spring.chatserver.global.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        UserValidator.validate(user);
        return new UserDetailsImpl(user);
    }
}
