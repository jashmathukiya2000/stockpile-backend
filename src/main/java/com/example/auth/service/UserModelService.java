package com.example.auth.service;

import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.userModel.LoginAddRequest;
import com.example.auth.decorator.userModel.UserModelAddRequest;
import com.example.auth.decorator.userModel.UserModelResponse;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

public interface UserModelService {
    UserModelResponse signUpUser(UserModelAddRequest signUpAddRequest, Role role);
    UserModelResponse login(LoginAddRequest loginAddRequest) throws InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException;

}
