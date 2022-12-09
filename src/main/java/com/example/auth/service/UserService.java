package com.example.auth.service;

import com.example.auth.decorator.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserService {
    UserResponse addUser(UserAddRequest userAddRequest);

    List<UserResponse> getAllUser();

    UserResponse getUser(String id) throws InvocationTargetException, IllegalAccessException;

    void updateUser(String id, UserAddRequest userAddRequest);

    void deleteUser(String id);


    List<UserResponse> getUserByAge(UserFilter userFilter);

    List<UserAggregationResponse> getUserBySalary(UserFilter userFilter);

    UserResponse addResult(String id, Result result) throws InvocationTargetException, IllegalAccessException;
}
