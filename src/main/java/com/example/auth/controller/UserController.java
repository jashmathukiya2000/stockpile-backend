package com.example.auth.controller;

import com.example.auth.common.config.constant.ResponseConstant;
import com.example.auth.common.config.enums.Role;
import com.example.auth.decorator.*;
import com.example.auth.service.ResultService;
import com.example.auth.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResultService resultService;
@SneakyThrows
    @RequestMapping(name = "addOrUpdateUser", value = "/add", method = RequestMethod.POST)
    public DataResponse<UserResponse> addOrUpdateUser(@RequestParam(required = false) String id, @RequestBody UserAddRequest userAddRequest) {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setData( userService.addOrUpdateUser(id, userAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }

    @SneakyThrows
    @RequestMapping(name = "getAllUser", value = "/getAll", method = RequestMethod.GET)
    public ListResponse<UserResponse> getAllUser() {
        ListResponse<UserResponse> listResponse = new ListResponse<>();
        listResponse.setData(userService.getAllUser());
        listResponse.setStatus(Response.getOkResponse());
        return listResponse;
    }

    @SneakyThrows
    @RequestMapping(name = "getUserById", value = "/user/{id}", method = RequestMethod.POST)
    public DataResponse<UserResponse> getUserById(@PathVariable String id) {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userService.getUser(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

    @RequestMapping(name = "deleteUser", value = "/delete/{id}", method = RequestMethod.DELETE)
    public DataResponse<Object> deleteUser(@PathVariable String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        userService.deleteUser(id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "getUserByAge", value = "/age", method = RequestMethod.POST)
    public ListResponse<UserResponse> getUserByAge(@RequestBody UserFilter userFilter) {
        ListResponse<UserResponse> listResponse = new ListResponse<>();
        listResponse.setData(userService.getUserByAge(userFilter));
        listResponse.setStatus(Response.getOkResponse());
        return listResponse;
    }

    @RequestMapping(name = "getUserBySalaryAggregation", value = "/salary", method = RequestMethod.POST)
    public ListResponse<UserAggregationResponse> getUserBySalary(@RequestBody UserFilter userFilter) {
        ListResponse<UserAggregationResponse> listResponse = new ListResponse<>();
        listResponse.setData(userService.getUserBySalary(userFilter));
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }

    @SneakyThrows
    @RequestMapping(name = "addResult", value = "/addResult/{id}", method = RequestMethod.POST)
    public DataResponse<UserResponse> addResult(@PathVariable String id, @RequestBody Result result) {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(resultService.addResult(id, result));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;
    }

    @RequestMapping(name = "getBySpi", value = "/spi", method = RequestMethod.GET)
    public ListResponse<UserSpiResponse> getBySpi(@RequestParam double spi) {
        ListResponse<UserSpiResponse> dataResponse = new ListResponse<>();
        dataResponse.setData(resultService.getBySpi(spi));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;
    }


    @RequestMapping(name = "signUpUser", value = "/signup", method = RequestMethod.POST)
    public DataResponse<SignUpResponse> signUpUser(@RequestBody SignUpAddRequest addSignUp, @RequestParam Role role) {
        DataResponse<SignUpResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userService.signUpUser(addSignUp, role));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;

    }

    @SneakyThrows
    @RequestMapping(name = "login", value = "/login/email", method = RequestMethod.POST)
    public DataResponse<SignUpResponse> login(@RequestBody LoginAddRequest loginAddRequest) {
        DataResponse<SignUpResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userService.login(loginAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.LOGIN_SUCCESSFULL));
        return dataResponse;
    }
}