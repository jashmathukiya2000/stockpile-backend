package com.example.auth.service;

import com.example.auth.decorator.UserAddRequest;
import com.example.auth.decorator.UserAggregationResponse;
import com.example.auth.decorator.UserFilter;
import com.example.auth.decorator.UserResponse;
import com.example.auth.decorator.pagination.FilterClass;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.UserSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserService {
    UserResponse addOrUpdateUser(String id, UserAddRequest userAddRequest) throws InvocationTargetException, IllegalAccessException;

    List<UserResponse> getAllUser();

    UserResponse getUser(String id) throws InvocationTargetException, IllegalAccessException;

    void deleteUser(String id);

    List<UserResponse> getUserByAge(UserFilter userFilter);

    List<UserAggregationResponse> getUserBySalary(UserFilter userFilter);

    Page<UserResponse> getAllUserByPagination(FilterClass filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pageRequest);
}


