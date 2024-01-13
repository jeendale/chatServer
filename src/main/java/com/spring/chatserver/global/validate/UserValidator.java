package com.spring.chatserver.global.validate;

import static com.spring.chatserver.global.meta.ResultCode.NOT_FOUND_USER;

import com.spring.chatserver.domain.user.entity.User;
import com.spring.chatserver.global.exception.GlobalException;

public class UserValidator {
    public static void validate(User user) {
        if (isNullUser(user)) {
            throw new GlobalException(NOT_FOUND_USER);
        }
    }

    private static boolean isNullUser(User user) {
        return user == null;
    }
}
