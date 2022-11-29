package com.example.auth.service;

import com.example.auth.Exception.UserCollectionException;
import com.example.auth.decorator.UserAddRequest;
import com.example.auth.model.User;

import java.util.List;

public interface UserService {
     User addUser(UserAddRequest userAddRequest) throws UserCollectionException;

     List<User> getAllUser() throws UserCollectionException;

     User getUser(String id) throws UserCollectionException;

    void  updateUser(String id,UserAddRequest userAddRequest) throws UserCollectionException;

     void deleteUser(String id) throws UserCollectionException;




}
