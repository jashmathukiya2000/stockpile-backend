package com.example.auth.service;

import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.customer.CustomerLoginAddRequest;
import com.example.auth.decorator.customer.CustomerAddRequest;
import com.example.auth.decorator.customer.CustomerResponse;
import com.example.auth.decorator.pagination.CustomerFilter;
import com.example.auth.decorator.pagination.CustomerSortBy;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface CustomerService {
    CustomerResponse addCustomer(CustomerAddRequest signUpAddRequest, Role role) throws InvocationTargetException, IllegalAccessException;

    CustomerResponse login(CustomerLoginAddRequest customerLoginAddRequest) throws InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException;

    Page<Customer> getAllCustomerByPagination(CustomerFilter filter, FilterSortRequest.SortRequest<CustomerSortBy> sort, PageRequest pagination);

    CustomerResponse getCustomerById(String id);

    List<CustomerResponse> getAllCustomer();

    void deleteCustomer(String id);

    void otpVerification(String otp,String email);


    void logout(String id);


    void forgetPassword(String email);

    void setPassword(String newPassword, String confirmPassword, String id);

}


