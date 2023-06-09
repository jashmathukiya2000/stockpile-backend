package com.example.auth.repository;

import com.example.auth.decorator.UserDateDetails;
import com.example.auth.decorator.UserDetailsExcelResponse;
import com.example.auth.decorator.UserEligibilityAggregation;
import com.example.auth.decorator.pagination.UserFilterData;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.UserSortBy;

import com.example.auth.decorator.user.*;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface UserCustomRepository {
    List<UserResponse> getByFilterAndSoftDeleteFalse(UserFilter userFilter);

    List<UserAggregationResponse> getUserByAggregation(UserFilter userFilter);

    public List<UserSpiResponse> getUserBySpi(double spi);

    public Page<UserResponse> getAllUserByPagination(UserFilterData filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pageRequest);


    public List<MaxSpiResponse> getUserByMaxSpi(String id);

    Page<UserSpiResponse> getUserDetailsByResultSpi(UserFilterData filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pageRequest) throws JSONException;


    Page<UserEligibilityAggregation> getUserEligibilityByAge(UserFilterData filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pagination) throws JSONException;

    List<UserDateDetails> userChartApi(int year);
}
