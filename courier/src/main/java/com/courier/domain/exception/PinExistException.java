package com.courier.domain.exception;

public class PinExistException extends RuntimeException {
    public PinExistException(String message) {
        super(message);
    }
}
