package com.spring.chatserver.domain.user.entity;

import com.spring.chatserver.domain.model.BaseEntity;
import com.spring.chatserver.domain.user.dto.request.SignUpReq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ch_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public User(SignUpReq signUpReq, String encodePassword) {
        this.username = signUpReq.getUsername();
        this.password = encodePassword;
    }
}
