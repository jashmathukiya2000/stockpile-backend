package com.example.auth.service;

import com.example.auth.Exception.UserCollectionException;
import com.example.auth.decorator.UserAddRequest;
import com.example.auth.decorator.UserResponse;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    @Override

    public UserResponse addUser(UserAddRequest userAddRequest) throws UserCollectionException {
        User user = modelMapper.map(userAddRequest, User.class);
        UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
        user= userRepository.save(user);

        return userResponse1;
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        if (users.size() > 0) {
            users.forEach(user -> {
                UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
                userResponseList.add(userResponse1);
            });
        }
        return  userResponseList;
    }

    @Override
    public UserResponse getUser(String id) throws UserCollectionException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            UserResponse userResponse1 = modelMapper.map(user.get(), UserResponse.class);
            return userResponse1;
        } else {
            throw new UserCollectionException(UserCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void updateUser(String id, UserAddRequest userAddRequest) throws UserCollectionException {
        Optional<User> user1 = userRepository.findById(id);
        Optional<User> user2 = userRepository.findById(userAddRequest.getName());
        if (user1.isPresent()) {
            if (user2.isPresent() && !user2.get().getId().equals(id)) {
                throw new UserCollectionException(UserCollectionException.UserAlreadyExist());
            }
            User user3 = user1.get();
            user3.setName(userAddRequest.getName());
            user3.setAge(userAddRequest.getAge());
            user3.setOccupation(userAddRequest.getOccupation());
            user3.setSalary(userAddRequest.getSalary());
            user3.setAddress(userAddRequest.getAddress());
            userRepository.save(user3);

        } else {
            throw new UserCollectionException(UserCollectionException.NotFoundException(id));
        }

    }

    @Override
    public void deleteUser(String id) throws UserCollectionException {

       Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
         userRepository.deleteById(id);
        } else {
            throw new UserCollectionException(UserCollectionException.NotFoundException(id));
        }

    }
}