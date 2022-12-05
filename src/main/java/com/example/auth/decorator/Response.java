package com.example.auth.decorator;

import com.example.auth.constant.ResponseConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response {
    HttpStatus code;
    String description;


    public static Response getOkResponse(String message) {
        return new Response(HttpStatus.OK, message);
    }

    public static Response getNotFoundResponse(String message) {
        return new Response(HttpStatus.OK, message);
    }

    public static Response getOkResponse() {
        return new Response(HttpStatus.OK, ResponseConstant.OK_DESCRIPTION);
    }

    public static Response getInternalServerErrorResponse() {
        return new Response(HttpStatus.OK, ResponseConstant.OK_DESCRIPTION);
    }

    public static Response getNotFoundResponse() {
        return new Response(HttpStatus.OK, ResponseConstant.NO_DESCRIPTION);
    }

        public static Response getUpdateResponse (String message){
            return new Response(HttpStatus.OK, ResponseConstant.UPDATED);
        }
    }

