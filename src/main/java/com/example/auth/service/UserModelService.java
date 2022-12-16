package com.example.auth.service;

import com.example.auth.common.config.enums.Role;
import com.example.auth.decorator.LoginAddRequest;
import com.example.auth.decorator.SignUpAddRequest;
import com.example.auth.decorator.SignUpResponse;

import java.lang.reflect.InvocationTargetException;

public interface UserModelService {
    SignUpResponse signUpUser(SignUpAddRequest addSignUp, Role role);
    SignUpResponse login(LoginAddRequest loginAddRequest) throws InvocationTargetException, IllegalAccessException;

}
