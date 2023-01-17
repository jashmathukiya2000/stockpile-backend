package com.example.auth.decorator;

import com.example.auth.commons.constant.ResponseConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@ToString
public class Response {
    HttpStatus code;
    String status;
    String description;

    public Response(HttpStatus ok, String ok1, String okDescription) {
        this.code = ok;
        this.status = ok1;
        this.description = okDescription;
    }

    public static Response getOkResponse(String message) {
        return new Response(HttpStatus.OK, ResponseConstant.OK,message);

    }

    public static Response getEmptyResponse(String message) {
        return new Response(HttpStatus.BAD_REQUEST,ResponseConstant.OK, message);

    }

    public static Response getInvaildResponse(String message) {
        return new Response(HttpStatus.BAD_REQUEST,ResponseConstant.OK,message);

    }

    public static Response getAlreadyExists(String message) {
        return new Response(HttpStatus.BAD_REQUEST,ResponseConstant.OK, message);

    }

    public static Response getInvalidRequestException(String message) {
        return new Response(HttpStatus.BAD_REQUEST,ResponseConstant.OK, message);
    }

    public static Response getNotFoundResponse(String message) {
        return new Response(HttpStatus.BAD_REQUEST,ResponseConstant.OK, message);
    }

    public static Response getOkResponse() {
        return new Response(HttpStatus.OK, ResponseConstant.OK,ResponseConstant.OK);
    }

    public static Response getInternalServerErrorResponse() {
        return new Response(HttpStatus.BAD_REQUEST, ResponseConstant.OK,ResponseConstant.OK);
    }

    public static Response getUpdateResponse() {
        return new Response(HttpStatus.OK, ResponseConstant.UPDATED,ResponseConstant.OK);
    }

        public Response getResponse(HttpStatus httpStatus,Object constant,Object constant1){
            return new Response(httpStatus, constant.toString(),constant1.toString());
        }
    }



