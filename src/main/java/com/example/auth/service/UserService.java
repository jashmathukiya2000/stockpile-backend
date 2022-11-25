package com.example.auth.service;

import com.example.auth.decorator.UserAddRequest;
import com.example.auth.model.User;

import java.util.List;

public interface UserService {
    public User createUser(UserAddRequest userAddRequest);
    public List<User>  getAllUser();
    public User getUser(String id);
}
