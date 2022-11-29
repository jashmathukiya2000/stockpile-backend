package com.example.auth.controller;

import com.example.auth.Exception.UserCollectionException;
import com.example.auth.decorator.*;
import com.example.auth.model.User;
import com.example.auth.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping(name = "addUser", value = "/add",method = RequestMethod.POST)
    public DataResponse<UserResponse> addUser(@RequestBody UserAddRequest userAddRequest) {
        DataResponse<UserResponse> dataResponse= new DataResponse<>();
        try {
            UserResponse addUser= userService.addUser(userAddRequest);
            dataResponse.setData(addUser);
            dataResponse.setStatus(Response.getOkResponse("done"));
        } catch (Exception e) {
            dataResponse.setStatus(Response.getInternalServerErrorResponse());
        }
        return dataResponse;
    }


    @RequestMapping(name = "getAllUser", value = "/get",method = RequestMethod.GET)
    public ListResponse<UserResponse> getAllUser() throws UserCollectionException {
        List<UserResponse> user = userService.getAllUser();
        ListResponse<UserResponse> dataResponse= new ListResponse<>();
        if (user.size() <= 0) {
            dataResponse.setStatus(Response.getNotFoundResponse());
        }
//        return new DataResponse<>(user, HttpStatus.OK);
        List<UserResponse> user1 = userService.getAllUser();
        dataResponse.setData(user);
        dataResponse.setStatus(Response.getOkResponse());

        return dataResponse;
    }


    @RequestMapping(name = "getUserById", value = "/get/{id}", method = RequestMethod.GET)
    public DataResponse<UserResponse> getUserById(@PathVariable String id) {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        try {
            UserResponse getUser=userService.getUser(id);
            dataResponse.setData(getUser);
            dataResponse.getData();
            dataResponse.setStatus(Response.getOkResponse("ok"));
        } catch (Exception e) {
      dataResponse.setStatus(Response.getNotFoundResponse("Not found"));
        }
        return dataResponse;
    }

    @RequestMapping(name = "updateUser", value = "/update/{id}", method = RequestMethod.PUT)
    public DataResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody UserAddRequest userAddRequest) {
        DataResponse<UserResponse> dataResponse= new DataResponse<>();
        try {
            userService.updateUser(id, userAddRequest);
           dataResponse.setStatus(Response.getUpdateResponse("updated successfully with given id"));
        }catch (UserCollectionException e) {
           dataResponse.setStatus(Response.getNotFoundResponse("no data is present of given id"));
        }
        return dataResponse;
    }


//
//
    @RequestMapping(name = "deleteUser", value = "/delete/{id}", method = RequestMethod.DELETE)
    public DataResponse<Object> deleteUser(@PathVariable String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        try {
            userService.deleteUser(id);
           dataResponse.setStatus(Response.getOkResponse("done"));

        } catch (UserCollectionException e) {
           dataResponse.setStatus(Response.getNotFoundResponse("not found"));
        }
        return dataResponse;
    }

}



