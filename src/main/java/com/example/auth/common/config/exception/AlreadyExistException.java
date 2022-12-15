package com.example.auth.common.config.exception;

public class AlreadyExistException extends RuntimeException {
    public  AlreadyExistException(String message){
        super(message);
    }
}
