package com.example.auth.exercise.service;

import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.exercise.decorator.UserInfoAddRequest;
import com.example.auth.exercise.decorator.UserInfoResponse;
import com.example.auth.exercise.model.UserInfo;
import com.example.auth.exercise.repository.UserInfoRepository;


import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import org.apache.commons.math3.util.Pair;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

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
    public void updateUser(String id, UserInfoAddRequest userAddRequest) throws NoSuchFieldException, IllegalAccessException {
        UserInfo user=findById(id);
        update(id,userAddRequest);
        userHelper.difference(userAddRequest,user);


    }

    @Override
    public List<UserInfoResponse> getAllUser() {
        List<UserInfo> users= userRepository.findAllBySoftDeleteFalse();
        List<UserInfoResponse> userResponses= new ArrayList<>();
        users.forEach(user -> {
            UserInfoResponse userResponse= modelMapper.map(user, UserInfoResponse.class);
            userResponses.add(userResponse);
        });
        return userResponses;

    }

    @Override
    public UserInfoResponse getUserById(String id) {
        UserInfo user=findById(id);
        UserInfoResponse userResponse= modelMapper.map(user, UserInfoResponse.class);
        return userResponse;
    }

    @Override
    public void deleteUser(String id) {
        UserInfo user=findById(id);
        user.setSoftDelete(true);
        userRepository.save(user);

    }

    @Override
    public Map<String, List<UserInfo>> getUsersByCity() {
        List<UserInfo> users = userRepository.findAllBySoftDeleteFalse();
        Map<String, List<UserInfo>> map = new HashMap<>();
        for (UserInfo user : users) {
            String city = user.getCity();
            List<UserInfo> list = map.getOrDefault(city, new ArrayList<>());
            list.add(user);
            map.put(city, list);
        }
        return map;
    }






    @Override
    public Map<Pair<String, String>, List<UserInfo>> getUsersByStateAndCity() {
        List<UserInfo> users = userRepository.findAllBySoftDeleteFalse();
        Map<Pair<String, String>, List<UserInfo>> map= new HashMap<>();
        for (UserInfo user : users) {
            String city=user.getCity();
            String state=user.getState();
            Pair<String, String> map1= new Pair<>(city,state);
            List<UserInfo> list=map.getOrDefault(map1,new ArrayList<>());
            list.add(user);
            map.put(map1, list);
        }
        return map;

    }

    @Override
    public Map<Triple<String, String, String>, List<UserInfo>> getUsersByCountryStateAndCity() {
        List<UserInfo> users = userRepository.findAllBySoftDeleteFalse();
        Map<Triple<String, String, String>, List<UserInfo>> map= new HashMap<>();
        for (UserInfo user : users) {
            String city=user.getCity();
            String state=user.getState();
            String country=user.getCountry();
            MutableTriple<String, String, String> map1= new MutableTriple<>(city,state,country);
            List<UserInfo> list= map.getOrDefault(map1, new ArrayList<>());
            list.add(user);
            map.put(map1,list);

        }
        return map;


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


    private void update(String id, UserInfoAddRequest userAddRequest) {
        UserInfo user=findById(id);
        if (userAddRequest.getCity()!=null){
            user.setCity(userAddRequest.getCity());
        }
        if (userAddRequest.getBirthDate()!=null){
            user.setBirthDate(userAddRequest.getBirthDate());
        }
        if (userAddRequest.getCountry()!=null){
            user.setCountry(userAddRequest.getCountry());
        }
        if (userAddRequest.getEmail()!=null){
            user.setEmail(userAddRequest.getEmail());
        }
        if (userAddRequest.getFirstName()!=null){
            user.setFirstName(userAddRequest.getFirstName());
        }
        if (userAddRequest.getLastName()!=null){
            user.setLastName(userAddRequest.getLastName());
        }
        if (userAddRequest.getState()!=null){
            user.setState(userAddRequest.getState());
        }
       userRepository.save(user);
    }


    UserInfo findById(String id){
        return userRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(()-> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }
}
