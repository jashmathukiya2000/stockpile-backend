package com.example.auth.repository;

import com.example.auth.decorator.*;

import java.util.List;

public interface UserCustomRepository {
 List<UserResponse> getByFilterAndSoftDeleteFalse(UserFilter userFilter)  ;
List<UserAggregationResponse>  getUserByAggregation(UserFilter userFilter);
 public List<UserSpiResponse> getUserBySpi( double spi );
 public List<OccupationResponse> getByOccupation(String occupation);

}
