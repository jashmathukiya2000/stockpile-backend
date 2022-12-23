package com.example.auth.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.decorator.user.UserAddRequest;
import com.example.auth.decorator.user.UserAggregationResponse;
import com.example.auth.decorator.user.UserFilter;
import com.example.auth.decorator.user.UserResponse;
import com.example.auth.decorator.pagination.UserFilterData;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.UserSortBy;
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
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;


    @Override
    public UserResponse addOrUpdateUser(String id, UserAddRequest userAddRequest) throws InvocationTargetException, IllegalAccessException {
        if (id != null) {
            User user1 = getUserModel(id);
            user1.setFirstName(userAddRequest.getFirstName());
            user1.setMiddleName(userAddRequest.getMiddleName());
            user1.setLastName(userAddRequest.getLastName());
            user1.setFullName();
            user1.setAge(userAddRequest.getAge());
            user1.setEmail(userAddRequest.getEmail());
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
        return userRepository.getAllUserByPagination(filter,sort,pageRequest);
    }


    public User getUserModel(String id) {
        return userRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_ID_NOT_FOUND));
    }

}
