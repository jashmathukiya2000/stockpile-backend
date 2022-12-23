package com.example.auth.service;

import com.example.auth.decorator.user.Result;
import com.example.auth.decorator.user.UserResponse;
import com.example.auth.decorator.user.UserSpiResponse;
import com.example.auth.commons.exception.EmptyException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ResultService {
    UserResponse addResult(String id, Result result) throws InvocationTargetException, IllegalAccessException, EmptyException;

    List<UserSpiResponse> getBySpi(double spi);
}
