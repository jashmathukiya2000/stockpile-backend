package com.example.auth.controller;

import com.example.auth.decorator.UserAddRequest;
import com.example.auth.decorator.UserResponse;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.UserService;
import com.example.auth.service.UserServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @PostMapping("/users")
    public User createUser(@RequestBody UserAddRequest userAddRequest) {
      return   userService.createUser(userAddRequest);
    }

    @GetMapping("/allUsers")
    public List<User> getAllUser(){
        return userService.getAllUser();
    }
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable String id){
        return userService.getUser(id);
    }

}
