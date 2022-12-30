package com.example.auth.controller;

import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.*;
import com.example.auth.decorator.customer.CustomerLoginAddRequest;
import com.example.auth.decorator.customer.CustomerSignupAddRequest;
import com.example.auth.decorator.customer.CustomerSignupResponse;
import com.example.auth.service.CustomerService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController("login")
public class CustomerController {
    private final CustomerService userModelService;

    public CustomerController(CustomerService userModelService) {
        this.userModelService = userModelService;
    }

    @RequestMapping(name = "signUpUser", value = "/signup", method = RequestMethod.POST)
    public DataResponse<CustomerSignupResponse> signUpUser(@RequestBody CustomerSignupAddRequest customerSignupAddRequest, @RequestParam Role role) {
        DataResponse<CustomerSignupResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userModelService.signUpUser(customerSignupAddRequest, role));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;

    }

    @SneakyThrows
    @RequestMapping(name = "login", value = "/login/email", method = RequestMethod.POST)
    public DataResponse<CustomerSignupResponse> login(@RequestBody CustomerLoginAddRequest customerLoginAddRequest) {
        DataResponse<CustomerSignupResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userModelService.login(customerLoginAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.LOGIN_SUCCESSFULL));
        return dataResponse;
    }
}
