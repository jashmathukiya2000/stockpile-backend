package com.example.auth.service;

import com.example.auth.decorator.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserService {
    UserResponse addOrUpdateUser(String id,UserAddRequest userAddRequest) throws InvocationTargetException, IllegalAccessException;

    List<UserResponse> getAllUser();

    UserResponse getUser(String id) throws InvocationTargetException, IllegalAccessException;


    void deleteUser(String id);

    List<UserResponse> getUserByAge(UserFilter userFilter);

    List<UserAggregationResponse> getUserBySalary(UserFilter userFilter);

}
