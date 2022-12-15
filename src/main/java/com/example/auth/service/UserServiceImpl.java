package com.example.auth.service;

import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
import com.example.auth.common.config.constant.MessageConstant;
import com.example.auth.common.config.enums.PasswordEncryptionType;
import com.example.auth.common.config.enums.Role;
import com.example.auth.common.config.exception.InvalidRequestException;
import com.example.auth.common.config.exception.NotFoundException;
import com.example.auth.common.config.utils.PasswordUtils;
import com.example.auth.decorator.*;
import com.example.auth.model.SignUpUser;
import com.example.auth.model.User;
import com.example.auth.repository.SignUpRepository;
import com.example.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final SignUpRepository signUpRepository;


    @Override
    public UserResponse addOrUpdateUser(String id, UserAddRequest userAddRequest) throws InvocationTargetException, IllegalAccessException {
        if (id != null) {
            User user1 = getUserModel(id);
            user1.setFirstName(userAddRequest.getFirstName());
            user1.setMiddleName(userAddRequest.getMiddleName());
            user1.setLastName(userAddRequest.getLastName());
            user1.setFullName();
            user1.setAge(userAddRequest.getAge());
            user1.setOccupation(userAddRequest.getOccupation());
            user1.setSalary(userAddRequest.getSalary());
            user1.setAddress(userAddRequest.getAddress());
            UserResponse userResponse1 = modelMapper.map(userAddRequest, UserResponse.class);
            nullAwareBeanUtilsBean.copyProperties(userResponse1, user1);
            userRepository.save(user1);
            return userResponse1;
        } else {
            User user = modelMapper.map(userAddRequest, User.class);
            user.setFullName();
            UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
            userRepository.save(user);
            return userResponse1;
        }
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAllBySoftDeleteFalse();
        List<UserResponse> userResponseList = new ArrayList<>();
        log.info("user:{}", users);
        users.forEach(user -> {
            UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
            userResponseList.add(userResponse1);
        });

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
        return userRepository.getUserByAggregation(userFilter);
    }

    @Override
    public SignUpResponse signUpUser(SignUpAddRequest signUpAddRequest, Role role) {
        SignUpUser signUpUser1 = modelMapper.map(signUpAddRequest, SignUpUser.class);
        SignUpResponse userResponse1 = modelMapper.map(signUpAddRequest, SignUpResponse.class);
        if (!signUpAddRequest.getPassword().equals(signUpAddRequest.getConfirmPassword())) {
            throw new InvalidRequestException(MessageConstant.INCORRECT_PASSWORD);
        }
        if (signUpUser1.getPassword() != null) {
            String password = PasswordUtils.encryptPassword(signUpUser1.getPassword());
            signUpUser1.setPassword(password);
            userResponse1.setPassword(password);
        }
        signUpUser1.setRole(role);
        userResponse1.setRole(role);
        signUpRepository.save(signUpUser1);
        return userResponse1;
    }

    @SneakyThrows
    @Override
    public SignUpResponse login(LoginAddRequest loginAddRequest) throws InvocationTargetException, IllegalAccessException {
        SignUpUser signUpUser = getUserByEmail(loginAddRequest.getEmail());
        String userPasswrod = signUpUser.getPassword();
        SignUpResponse signUpResponse = modelMapper.map(loginAddRequest, SignUpResponse.class);
        boolean passwords = PasswordUtils.isPasswordAuthenticated(loginAddRequest.getPassword(), userPasswrod, PasswordEncryptionType.BCRYPT);
        if (passwords) {
            nullAwareBeanUtilsBean.copyProperties(signUpResponse, signUpUser);
        } else {
            throw new InvalidRequestException(MessageConstant.INCORRECT_PASSWORD);
        }
        return signUpResponse;
    }

    @SneakyThrows
    public User getUserModel(String id) {
        return userRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_ID_NOT_FOUND));
    }

    @SneakyThrows
    public SignUpUser getUserByEmail(String email) throws NotFoundException {
        return signUpRepository.findUserByEmailAndSoftDeleteIsFalse(email).orElseThrow(() -> new NotFoundException(MessageConstant.USER_NOT_FOUND));
    }

}

