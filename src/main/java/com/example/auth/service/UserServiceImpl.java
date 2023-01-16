package com.example.auth.service;

import com.example.auth.commons.JWTUser;
import com.example.auth.commons.JwtTokenUtil;
import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.UserFilterData;
import com.example.auth.decorator.pagination.UserSortBy;
import com.example.auth.decorator.user.*;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public UserResponse addOrUpdateUser(String id, UserAddRequest userAddRequest) throws InvocationTargetException, IllegalAccessException {
        if (id != null) {
            User user1 = getUserModel(id);
//            user1.setFirstName(userAddRequest.getFirstName());
//            user1.setMiddleName(userAddRequest.getMiddleName());
//            user1.setLastName(userAddRequest.getLastName());
//            user1.setFullName();
//            user1.setAge(userAddRequest.getAge());
//            user1.setEmail(userAddRequest.getEmail());
//            user1.setOccupation(userAddRequest.getOccupation());
//            user1.setSalary(userAddRequest.getSalary());
//            user1.setAddress(userAddRequest.getAddress());
            nullAwareBeanUtilsBean.copyProperties(user1, userAddRequest);
            userRepository.save(user1);
            return modelMapper.map(user1, UserResponse.class);

//            UserResponse userResponse1 = modelMapper.map(userAddRequest, UserResponse.class);
//            nullAwareBeanUtilsBean.copyProperties(userResponse1, user1);
//            userRepository.save(user1);
//            return userResponse1;
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
        users.forEach(user -> {
            UserResponse userResponse1 = modelMapper.map(user, UserResponse.class);
            userResponseList.add(userResponse1);
        });

        return userResponseList;
    }

    @Override
    public UserResponse getUser(String id) throws InvocationTargetException, IllegalAccessException {
        User user = getUserModel(id);
        return modelMapper.map(user, UserResponse.class);
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
    public Page<UserResponse> getAllUserByPagination(UserFilterData filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pageRequest) {
        return userRepository.getAllUserByPagination(filter, sort, pageRequest);
    }

    @Override
    public List<MaxSpiResponse> getUserByMaxSpi(String id) {
        return userRepository.getUserByMaxSpi(id);
    }

    @Override
    public UserResponse getToken(String id) throws InvocationTargetException, IllegalAccessException {
         User user = getUserModel(id);
         UserResponse userResponse = new UserResponse();
         userResponse.setRole(user.getRole());
         JWTUser jwtUser = new JWTUser(id, Collections.singletonList(userResponse.getRole().toString()));
         String token = jwtTokenUtil.generateToken(jwtUser);
         nullAwareBeanUtilsBean.copyProperties(userResponse, user);
          userResponse.setToken(token);
//        userResponse.setId(user.getId());
          return userResponse;
    }


    public User getUserModel(String id) {
        return userRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_ID_NOT_FOUND));
    }

}
