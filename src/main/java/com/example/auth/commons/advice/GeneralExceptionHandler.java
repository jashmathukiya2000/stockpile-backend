package com.example.auth.commons.advice;

import com.example.auth.commons.exception.AlreadyExistException;
import com.example.auth.commons.exception.EmptyException;
import com.example.auth.commons.exception.InvalidRequestException;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.decorator.DataResponse;
import com.example.auth.commons.decorator.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GeneralExceptionHandler {
    @Autowired
    Response response;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DataResponse<Object>> getError(HttpServletRequest req, NotFoundException ex) {
        return new ResponseEntity<>(new DataResponse<>(null, Response.getNotFoundResponse(ex.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<DataResponse<Object>> getError(HttpServletRequest req, AlreadyExistException ex) {
        return new ResponseEntity<>(new DataResponse<>(null, Response.getAlreadyExists(ex.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(EmptyException.class)
    public ResponseEntity<DataResponse<Object>> getError(HttpServletRequest req, EmptyException ex) {
        return new ResponseEntity<>(new DataResponse<>(null, Response.getEmptyResponse(ex.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<DataResponse<Object>> getError(HttpServletRequest req, InvalidRequestException ex) {
        return new ResponseEntity<>(new DataResponse<>(null, Response.getInvaildResponse(ex.getMessage())), HttpStatus.OK);
    }
}