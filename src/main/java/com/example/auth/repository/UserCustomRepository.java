package com.example.auth.repository;

import com.example.auth.decorator.UserAggregationResponse;
import com.example.auth.decorator.UserFilter;
import com.example.auth.decorator.UserResponse;

import java.util.List;

public interface UserCustomRepository {
 List<UserResponse> getByFilterAndSoftDeleteFalse(UserFilter userFilter)  ;
List<UserAggregationResponse>  getUserByAggregation(UserFilter userFilter);
}
