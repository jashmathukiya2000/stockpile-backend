package com.example.auth.repository;

import com.example.auth.decorator.UserFilter;
import com.example.auth.decorator.UserResponse;

import java.util.List;

public interface UserCustomRepository {
 List<UserResponse> findByAgeAndSoftDeleteFalse(UserFilter userFilter);
// List<UserResponse> findOccupationAndSoftDeleteFalse(UserFilter userFilter);
}
