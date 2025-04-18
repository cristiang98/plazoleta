package com.plaza.application.exception;

public class RestaurantExistException extends RuntimeException {
    public RestaurantExistException(String message) {
        super(message);
    }
}
