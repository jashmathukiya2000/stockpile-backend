package com.example.auth.common.config.exception;

public class InvalidRequestException extends RuntimeException{
    public  InvalidRequestException(String message){
        super(message);
    }
}
