package com.example.auth.service;

import com.example.auth.constant.MessageConstant;
import com.example.auth.decorator.*;
import com.example.auth.exception.EmptyException;
import com.example.auth.exception.NotFoundException;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    @Override
    public UserResponse addUser(UserAddRequest userAddRequest) {
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
    public UserResponse getUser(String id) throws InvocationTargetException, IllegalAccessException {
        User user = getUserModel(id);
        UserResponse userResponse = new UserResponse();
        nullAwareBeanUtilsBean.copyProperties(userResponse, user);
        return userResponse;
    }

    @Override
    public void updateUser(String id, UserAddRequest userAddRequest) {
        User user1 = getUserModel(id);
        if (user1 != null && user1.isSoftDelete() == false) {
            user1.setName(userAddRequest.getName());
            user1.setAge(userAddRequest.getAge());
            user1.setOccupation(userAddRequest.getOccupation());
            user1.setSalary(userAddRequest.getSalary());
            user1.setAddress(userAddRequest.getAddress());
            userRepository.save(user1);

        } else {
            throw new NotFoundException(MessageConstant.USER_ID_NOT_FOUND);
        }

    }

    @Override
    public void deleteUser(String id) {
        User user = getUserModel(id);
        user.setSoftDelete(true);
        userRepository.save(user);
    }


    @Override
    public List<UserResponse> getUserByAge(UserFilter userFilter) {
        return userRepository.getByFilterAndSoftDeleteFalse(userFilter);
    }

    @Override
    public List<UserAggregationResponse> getUserBySalary(UserFilter userFilter) {
        System.out.println("inside service impl");
        return userRepository.getUserByAggregation(userFilter);
    }

    @Override
    public UserResponse addResult(String id, Result result) throws InvocationTargetException, IllegalAccessException {
        List<Result> results = new ArrayList<>();
        User user1 = getUserModel(id);
        double sum = 0, cgpa = 0;
        if (!CollectionUtils.isEmpty(user1.getResult())) {
            results = user1.getResult();
            checkResultValidation(result);
        }
        for (Result result1 : results) {
            if (result.getSemester() == result1.getSemester()) {
                throw new EmptyException(MessageConstant.SEMESTER_ALREADY_EXIST);
            }
        }
        results.add(result);
        for (Result result1 : results) {
            sum += result1.getSpi();
        }
        cgpa = sum / results.size();
        cgpa = Double.parseDouble(new DecimalFormat("##.##").format(cgpa));

        user1.setCgpa(cgpa);
        user1.setResult(results);
        userRepository.save(user1);
        UserResponse userResponse = new UserResponse();
        nullAwareBeanUtilsBean.copyProperties(userResponse, user1);
        return userResponse;

    }

    public User getUserModel(String id) {
        return userRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_ID_NOT_FOUND));
    }

    public void checkResultValidation(Result result) throws InvocationTargetException, IllegalAccessException {
        if (result.getSemester() > 8) {
            throw new NotFoundException(MessageConstant.SEMESTER_NOT_VALID);
        }
        if (result.getSpi() > 10) {
            throw new NotFoundException(MessageConstant.SPI_NOT_VALID);
        }
    }
}