package com.example.auth.commons.decorator;

import com.example.auth.commons.decorator.DataResponse;
import lombok.Data;

@Data
public class TokenResponse<T> extends DataResponse<T> {
    String token;
}
