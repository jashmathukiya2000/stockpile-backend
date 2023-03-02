package com.example.auth.exercise.service;

import com.example.auth.exercise.decorator.UserInfoAddRequest;
import com.example.auth.exercise.decorator.UserInfoResponse;
import com.example.auth.exercise.model.UserInfo;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.util.Pair;


import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserInfoService {
    
    UserInfoResponse addUser(UserInfoAddRequest userAddRequest);

    Map<String, List<UserInfo>> getUsersByCity();


    Map<Pair<String, String>, List<UserInfo>> getUsersByStateAndCity();

    Map<Triple<String, String, String>, List<UserInfo>> getUsersByCountryStateAndCity();

    Set<String> getAllCity();

    List<UserInfo> sortByBirthAndFirstName();

    Map<String, UserInfo> getUserByEmail();

    List<UserInfo> getUserByAge();

}
