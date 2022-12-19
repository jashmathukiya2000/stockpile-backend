package com.example.auth.service;

import com.example.auth.decorator.Result;
import com.example.auth.decorator.UserAddRequest;
import com.example.auth.decorator.UserResponse;
import com.example.auth.decorator.UserSpiResponse;
import com.example.auth.common.config.exception.EmptyException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ResultService {
    UserResponse addResult(String id, Result result) throws InvocationTargetException, IllegalAccessException, EmptyException;

    List<UserSpiResponse> getBySpi(double spi);
}
