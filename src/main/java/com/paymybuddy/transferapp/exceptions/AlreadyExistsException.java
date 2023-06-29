package com.paymybuddy.transferapp.exceptions;

public class AlreadyExistsException extends RuntimeException {

    private final String message;

    public AlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
