package com.example.auth.controller;

import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.*;
import com.example.auth.decorator.userModel.LoginAddRequest;
import com.example.auth.decorator.userModel.UserModelAddRequest;
import com.example.auth.decorator.userModel.UserModelResponse;
import com.example.auth.service.UserModelService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController("login")
public class UserModelController {
    private final UserModelService userModelService;

    public UserModelController(UserModelService userModelService) {
        this.userModelService = userModelService;
    }

    @RequestMapping(name = "signUpUser", value = "/signup", method = RequestMethod.POST)
    public DataResponse<UserModelResponse> signUpUser(@RequestBody UserModelAddRequest addSignUp, @RequestParam Role role) {
        DataResponse<UserModelResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userModelService.signUpUser(addSignUp, role));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;

    }

    @SneakyThrows
    @RequestMapping(name = "login", value = "/login/email", method = RequestMethod.POST)
    public DataResponse<UserModelResponse> login(@RequestBody LoginAddRequest loginAddRequest) {
        DataResponse<UserModelResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userModelService.login(loginAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.LOGIN_SUCCESSFULL));
        return dataResponse;
    }
}
