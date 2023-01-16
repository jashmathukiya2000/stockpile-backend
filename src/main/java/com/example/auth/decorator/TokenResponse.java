package com.example.auth.decorator;

import lombok.Data;

@Data
public class TokenResponse<T> extends DataResponse<T> {
    String token;
}
