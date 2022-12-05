package com.example.auth.controller;

import com.example.auth.constant.ResponseConstant;
import com.example.auth.decorator.*;
import com.example.auth.exception.UserCollectionException;
import com.example.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping(name = "addUser", value = "/add", method = RequestMethod.POST)
    public DataResponse<UserResponse> addUser(@RequestBody UserAddRequest userAddRequest) {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        try {
            dataResponse.setData(userService.addUser(userAddRequest));
            dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        } catch (Exception e) {
            dataResponse.setStatus(Response.getInternalServerErrorResponse());
        }
        return dataResponse;
    }


    @RequestMapping(name = "getAllUser", value = "/get", method = RequestMethod.GET)
    public ListResponse<UserResponse> getAllUser() throws UserCollectionException {
        List<UserResponse> user = userService.getAllUser();
        ListResponse<UserResponse> listResponse = new ListResponse<>();
        if (user.size() > 0) {
            listResponse.setStatus(Response.getNotFoundResponse());
        }
        listResponse.setData(user);
        listResponse.setStatus(Response.getOkResponse());

        return listResponse;
    }


    @RequestMapping(name = "getUserById", value = "/get/{id}", method = RequestMethod.GET)
    public DataResponse<UserResponse> getUserById(@PathVariable String id) {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        try {
            dataResponse.setData(userService.getUser(id));
            dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK_DESCRIPTION));
        } catch (Exception e) {
            dataResponse.setStatus(Response.getNotFoundResponse(ResponseConstant.NOT_FOUND));
        }
        return dataResponse;
    }

    @RequestMapping(name = "updateUser", value = "/update/{id}", method = RequestMethod.PUT)
    public DataResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody UserAddRequest userAddRequest) {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        try {
            userService.updateUser(id, userAddRequest);
            dataResponse.setStatus(Response.getUpdateResponse(ResponseConstant.UPDATED_SUCCESSFULLY));
        } catch (UserCollectionException e) {
            dataResponse.setStatus(Response.getNotFoundResponse(ResponseConstant.NOT_FOUND));
        }
        return dataResponse;
    }

    @RequestMapping(name = "deleteUser", value = "/delete/{id}", method = RequestMethod.DELETE)
    public DataResponse<Object> deleteUser(@PathVariable String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        try {
            userService.deleteUser(id);
            dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));
        } catch (UserCollectionException e) {
            dataResponse.setStatus(Response.getNotFoundResponse(ResponseConstant.NOT_FOUND));
        }
        return dataResponse;
    }
@RequestMapping(name = "getUserByAge", value = "get/Age", method = RequestMethod.POST)
    public ListResponse<UserResponse> getUserByAge(@RequestBody UserFilter userFilter){
        ListResponse<UserResponse> listResponse= new ListResponse<>();
        try{
            listResponse.setData(userService.getUserByAge(userFilter));
        } catch (Exception e) {
            listResponse.setStatus(Response.getNotFoundResponse(ResponseConstant.NOT_FOUND));
        }
        return listResponse;
    }

    @RequestMapping(name = "getUserBySalaryAggregation", value = "get", method = RequestMethod.POST)
    public ListResponse<UserAggregationResponse> getUserBySalary(@RequestBody UserFilter userFilter){
        ListResponse<UserAggregationResponse> listResponse= new ListResponse<>();
        try{
            listResponse.setData(userService.getUserBySalary(userFilter));
        } catch (Exception e) {
            listResponse.setStatus(Response.getNotFoundResponse(ResponseConstant.DATA_NOT_FOUND));
        }
        return listResponse;
    }
}



