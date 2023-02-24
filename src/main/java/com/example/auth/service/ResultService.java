package com.example.auth.service;

import com.example.auth.commons.exception.EmptyException;
import com.example.auth.decorator.user.Result;
import com.example.auth.decorator.user.UserResponse;
import com.example.auth.decorator.user.UserSpiResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ResultService {
    UserResponse addResult(String id, Result result) ;

    List<UserSpiResponse> getBySpi(double spi);
}
