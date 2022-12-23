package com.example.auth.commons.exception;

public class AlreadyExistException extends RuntimeException {
    public  AlreadyExistException(String message){
        super(message);
    }
}
