package com.example.auth.service;

import com.example.auth.decorator.UserAddRequest;
import com.example.auth.decorator.UserFilter;
import com.example.auth.decorator.UserResponse;
import com.example.auth.exception.UserCollectionException;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        user = userRepository.save(user);

        return userResponse1;
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        if (users.size() > 0) {
            users.forEach(user -> {
                UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
                if (userResponse1.isSoftDelete() == false) {
                    userResponseList.add(userResponse1);
                }
            });
        }

        return userResponseList;
    }

    @Override
    public UserResponse getUser(String id) throws UserCollectionException {
        User user = getUserModel(id);

        if (user != null && user.isSoftDelete() == false) {
            UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
            return userResponse1;
        } else {
            throw new UserCollectionException(UserCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void updateUser(String id, UserAddRequest userAddRequest) throws UserCollectionException {
        User user1 = getUserModel(id);
        if (user1 != null && user1.isSoftDelete() == false) {
            user1.setName(userAddRequest.getName());
            user1.setAge(userAddRequest.getAge());
            user1.setOccupation(userAddRequest.getOccupation());
            user1.setSalary(userAddRequest.getSalary());
            user1.setAddress(userAddRequest.getAddress());
            userRepository.save(user1);

        } else {
            throw new UserCollectionException(UserCollectionException.NotFoundException(id));
        }

    }

    @Override
    public void deleteUser(String id) throws UserCollectionException {
        User user = getUserModel(id);
        if (user != null) {
            user.setSoftDelete(true);
            userRepository.save(user);
        } else {

            throw new UserCollectionException(UserCollectionException.NotFoundException(id));
        }

    }

    @Override
    public List<UserResponse> getUserByAge(UserFilter userFilter) {
        return userRepository.findByAgeAndSoftDeleteFalse(userFilter);
    }

    public User getUserModel(String id) {
        return userRepository.findById(id).orElseThrow();
    }
}