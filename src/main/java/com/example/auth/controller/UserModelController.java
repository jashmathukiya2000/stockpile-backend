package com.example.auth.controller;

import com.example.auth.common.config.constant.ResponseConstant;
import com.example.auth.common.config.enums.Role;
import com.example.auth.decorator.*;
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
    public DataResponse<SignUpResponse> signUpUser(@RequestBody SignUpAddRequest addSignUp, @RequestParam Role role) {
        DataResponse<SignUpResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userModelService.signUpUser(addSignUp, role));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;

    }

    @SneakyThrows
    @RequestMapping(name = "login", value = "/login/email", method = RequestMethod.POST)
    public DataResponse<SignUpResponse> login(@RequestBody LoginAddRequest loginAddRequest) {
        DataResponse<SignUpResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userModelService.login(loginAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.LOGIN_SUCCESSFULL));
        return dataResponse;
    }
}
