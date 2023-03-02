package com.example.auth.controller;

import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.DataResponse;
import com.example.auth.commons.decorator.ListResponse;
import com.example.auth.commons.decorator.Response;
import com.example.auth.commons.enums.Role;
import com.example.auth.exercise.decorator.UserInfoAddRequest;
import com.example.auth.exercise.decorator.UserInfoResponse;
import com.example.auth.exercise.model.UserInfo;
import com.example.auth.exercise.service.UserInfoService;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.util.Pair;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("userInfo")
public class UserInfoController {
    private final UserInfoService userService;

    public UserInfoController(UserInfoService userService) {
        this.userService = userService;
    }

    @RequestMapping(name = "addUser",value = "/add/user", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<UserInfoResponse> addUser(@RequestBody UserInfoAddRequest userAddRequest){
        DataResponse<UserInfoResponse> dataResponse= new DataResponse<>();
        dataResponse.setData(userService.addUser(userAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));

        return dataResponse;

    }


    @RequestMapping(name = "getUsersByCity",value = "/get/users/city", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Map<String, List<UserInfo>>> getUsersByCity()  {
        DataResponse<Map<String, List<UserInfo>>> dataResponse = new DataResponse<>();
      dataResponse.setData(userService.getUsersByCity());
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }
       @RequestMapping(name = "getUsersByStateAndCity",value = "/get/users/state/city", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Map<Pair<String,String>, List<UserInfo>>> getUsersByStateAndCity()  {
        DataResponse<Map<Pair<String,String>, List<UserInfo>>> dataResponse = new DataResponse<>();
      dataResponse.setData(userService.getUsersByStateAndCity());
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

    @RequestMapping(name = "getUsersByCountryStateAndCity",value = "/get/users/country/city", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Map<Triple<String,String,String>, List<UserInfo>>> getUsersByCountryStateAndCity()  {
        DataResponse<Map<Triple<String,String,String>, List<UserInfo>>> dataResponse = new DataResponse<>();
      dataResponse.setData(userService.getUsersByCountryStateAndCity());
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }


    @RequestMapping(name = "getAllCity",value = "/get/all/city", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Set<String>> getAllCity()  {
        DataResponse<Set<String>> dataResponse = new DataResponse<>();
      dataResponse.setData(userService.getAllCity());
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

  @RequestMapping(name = "sortByBirthAndFirstName",value = "/sort/birth/firstName", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<List<UserInfo>> sortByBirthAndFirstName()  {
        DataResponse<List<UserInfo>> dataResponse = new DataResponse<>();
      dataResponse.setData(userService.sortByBirthAndFirstName());
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

// @RequestMapping(name = "getUserByEmail",value = "/get/user/email", method = RequestMethod.GET)
//    @Access(levels = Role.ANONYMOUS)
//    public DataResponse<Map<String, UserInfo>>  sortByBirthAndFirstName()  {
//        DataResponse<Map<String, UserInfo>>  dataResponse = new DataResponse<>();
//      dataResponse.setData(userService.getUserByEmail());
//        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
//        return dataResponse;
//    }
//

    @RequestMapping(name = "getUserByAge",value = "/get/user/age", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<List<UserInfo>>  getUserByAge()  {
        DataResponse<List< UserInfo>>  dataResponse = new DataResponse<>();
      dataResponse.setData(userService.getUserByAge());
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }











}
