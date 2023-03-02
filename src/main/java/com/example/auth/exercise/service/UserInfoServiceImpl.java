package com.example.auth.exercise.service;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.exercise.decorator.UserInfoAddRequest;
import com.example.auth.exercise.decorator.UserInfoResponse;
import com.example.auth.exercise.model.UserInfo;
import com.example.auth.exercise.repository.UserInfoRepository;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.util.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserHelper userHelper;


    public UserInfoServiceImpl(UserInfoRepository userRepository, ModelMapper modelMapper, UserHelper userHelper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userHelper = userHelper;
    }

    @Override
    public UserInfoResponse addUser(UserInfoAddRequest userAddRequest) {
        UserInfo user = modelMapper.map(userAddRequest, UserInfo.class);
        user.setBirthDate(new Date());
        userRepository.save(user);

        UserInfoResponse userResponse = modelMapper.map(user, UserInfoResponse.class);
        return userResponse;
    }


    @Override
    public Map<String, List<UserInfo>> getUsersByCity() {
        List<UserInfo> users = userRepository.findAllBySoftDeleteFalse();
        return users.stream().collect(Collectors.groupingBy(userInfo -> new String(userInfo.getCity())));
    }


    @Override
    public Map<Pair<String, String>, List<UserInfo>> getUsersByStateAndCity() {
        List<UserInfo> users = userRepository.findAllBySoftDeleteFalse();
        return users.stream().collect(Collectors.groupingBy(userInfo -> new Pair<>(userInfo.getCity(), userInfo.getState())));
    }

    public Map<Triple<String, String, String>, List<UserInfo>> getUsersByCountryStateAndCity() {
        List<UserInfo> users = userRepository.findAllBySoftDeleteFalse();
        return users.stream()
                .collect(Collectors.groupingBy(user -> new ImmutableTriple<>(user.getCity(), user.getState(), user.getCountry())));
    }

    @Override
    public Set<String> getAllCity() {
        Set<String> cityNames = userRepository.findAllBySoftDeleteFalse()
                .stream()
                .map(UserInfo::getCity)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return cityNames;


    }

    @Override
    public List<UserInfo> sortByBirthAndFirstName() {
        List<UserInfo> users = userRepository.findAllBySoftDeleteFalse();
        return users.stream().sorted(Comparator.comparing(UserInfo::getBirthDate).thenComparing(UserInfo::getFirstName))
                .collect(Collectors.toList());

    }

    @Override
    public Map<String, UserInfo> getUserByEmail() {
        List<UserInfo> users = userRepository.findAllBySoftDeleteFalse();
        return  null;

    }

    @Override
    public List<UserInfo> getUserByAge() {
        List<UserInfo> allUsers = userRepository.findAll();
        return allUsers.stream()
                .filter(user -> Period.between(user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears() >= 18)
                .collect(Collectors.toList());
    }



}
