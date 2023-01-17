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
    CustomerResponse addCustomer(CustomerAddRequest signUpAddRequest, Role role);

    CustomerResponse login(CustomerLoginAddRequest customerLoginAddRequest) throws InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException;

    Page<Customer> getAllCustomerByPagination(CustomerFilter filter, FilterSortRequest.SortRequest<CustomerSortBy> sort, PageRequest pagination);

    CustomerResponse getCustomerById(String id);

    List<CustomerResponse> getAllCustomer();

    Object deleteCustomer(String id);
}


