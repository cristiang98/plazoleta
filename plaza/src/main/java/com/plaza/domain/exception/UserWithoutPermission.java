package com.plaza.domain.exception;

public class UserWithoutPermission extends RuntimeException {

    public UserWithoutPermission(String message) {
        super(message);
    }
}
