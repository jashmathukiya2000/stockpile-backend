package com.example.auth.decorator;

import com.example.auth.common.config.constant.ResponseConstant;
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

    } public static Response getEmptyResponse(String message) {
        return new Response(HttpStatus.BAD_REQUEST, message);

    } public static Response getInvaildResponse(String message) {
        return new Response(HttpStatus.BAD_REQUEST, message);

    }

    public static Response getAlreadyExists(String message) {
        return new Response(HttpStatus.BAD_REQUEST, message);

    }   public static Response getInvalidRequestException(String message) {
        return new Response(HttpStatus.BAD_REQUEST, message);
    }

    public static Response getNotFoundResponse(String message) {
        return new Response(HttpStatus.BAD_REQUEST, message);
    }

    public static Response getOkResponse() {
        return new Response(HttpStatus.OK, ResponseConstant.OK);
    }

    public static Response getInternalServerErrorResponse() {
        return new Response(HttpStatus.BAD_REQUEST, ResponseConstant.OK);
    }

        public static Response getUpdateResponse (String message){
            return new Response(HttpStatus.OK, ResponseConstant.UPDATED);
        }
    }

