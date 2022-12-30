package com.example.auth.service;

import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.customer.CustomerLoginAddRequest;
import com.example.auth.decorator.customer.CustomerSignupAddRequest;
import com.example.auth.decorator.customer.CustomerSignupResponse;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

public interface CustomerService {
    CustomerSignupResponse signUpUser(CustomerSignupAddRequest signUpAddRequest, Role role);
    CustomerSignupResponse login(CustomerLoginAddRequest customerLoginAddRequest) throws InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException;

}
