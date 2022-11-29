package com.example.auth.service;

import com.example.auth.Exception.UserCollectionException;
import com.example.auth.decorator.UserAddRequest;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User addUser(UserAddRequest userAddRequest) throws UserCollectionException {
        User user = modelMapper.map(userAddRequest, User.class);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        if (users.size() > 0) {
            return users;
        } else {
            return new ArrayList<User>();
        }
    }

    @Override
    public User getUser(String id) throws UserCollectionException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
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