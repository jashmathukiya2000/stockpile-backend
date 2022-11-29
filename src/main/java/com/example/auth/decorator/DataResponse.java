package com.example.auth.decorator;

import com.google.cloud.BaseWriteChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse<T> {
    T data;
    Response status;



}
