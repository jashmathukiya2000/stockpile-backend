package com.example.auth.service;

import com.example.auth.Exception.UserCollectionException;
import com.example.auth.decorator.UserAddRequest;
import com.example.auth.decorator.UserResponse;

import java.util.List;

public interface UserService {
     UserResponse addUser(UserAddRequest userAddRequest) throws UserCollectionException;

     List<UserResponse> getAllUser() throws UserCollectionException;

     UserResponse getUser(String id) throws UserCollectionException;

    void  updateUser(String id,UserAddRequest userAddRequest) throws UserCollectionException;

   void deleteUser(String id) throws UserCollectionException;




}
