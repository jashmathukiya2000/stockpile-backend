package com.example.auth.repository;

import com.example.auth.decorator.*;
import com.example.auth.decorator.pagination.FilterClass;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.UserSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserCustomRepository {
 List<UserResponse> getByFilterAndSoftDeleteFalse(UserFilter userFilter)  ;
List<UserAggregationResponse>  getUserByAggregation(UserFilter userFilter);
 public List<UserSpiResponse> getUserBySpi( double spi );
 public List<OccupationResponse> getByOccupation(String occupation);
 public Page<UserResponse> getAllUserByPagination(FilterClass filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pageRequest);

}
