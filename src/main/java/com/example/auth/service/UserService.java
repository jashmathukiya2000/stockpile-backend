package com.example.auth.service;

import com.example.auth.decorator.*;
import com.example.auth.exception.UserCollectionException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserService {
    UserResponse addUser(UserAddRequest userAddRequest) throws UserCollectionException;

    List<UserResponse> getAllUser() throws UserCollectionException;

    UserResponse getUser(String id) throws UserCollectionException;

    void updateUser(String id, UserAddRequest userAddRequest) throws UserCollectionException;

    void deleteUser(String id) throws UserCollectionException;


    List<UserResponse> getUserByAge(UserFilter userFilter);

    List<UserAggregationResponse> getUserBySalary(UserFilter userFilter);

    UserResponse addResult(String id, Result result) throws InvocationTargetException, IllegalAccessException;
}
