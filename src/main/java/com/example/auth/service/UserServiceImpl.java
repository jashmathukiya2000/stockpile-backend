package com.example.auth.service;

import com.example.auth.decorator.UserAddRequest;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
@Autowired
private UserRepository userRepository;
@Autowired
    ModelMapper modelMapper;
    @Override

    public User createUser(UserAddRequest userAddRequest) {
        User user = modelMapper.map( userAddRequest,User.class);
          return  userRepository.save(user);
    }
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String id) {
   return   userRepository.findById(id).get();
    }
    }