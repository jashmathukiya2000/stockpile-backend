package com.example.auth.commons.exception;

public class InvalidRequestException extends RuntimeException{
    public  InvalidRequestException(String message){
        super(message);
    }
}
