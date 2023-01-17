package com.example.auth.service;

import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.enums.PasswordEncryptionType;
import com.example.auth.commons.enums.Role;
import com.example.auth.commons.exception.InvalidRequestException;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.utils.PasswordUtils;
import com.example.auth.decorator.customer.CustomerAddRequest;
import com.example.auth.decorator.customer.CustomerLoginAddRequest;
import com.example.auth.decorator.customer.CustomerResponse;
import com.example.auth.decorator.pagination.CustomerFilter;
import com.example.auth.decorator.pagination.CustomerSortBy;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.model.Customer;
import com.example.auth.repository.CustomerRepository;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordUtils passwordUtils;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, PasswordUtils passwordUtils) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.passwordUtils = passwordUtils;
    }


    @Override
    public CustomerResponse addCustomer(CustomerAddRequest customerAddRequest, Role role) {
        Customer signUpUser1 = modelMapper.map(customerAddRequest, Customer.class);
        CustomerResponse userResponse1 = modelMapper.map(customerAddRequest, CustomerResponse.class);
        if (signUpUser1.getPassword() != null) {
            String password = password(signUpUser1.getPassword());
            signUpUser1.setPassword(password);
            userResponse1.setPassword(password);
        }
        checkValidation(customerAddRequest);
        signUpUser1.setRole(role);
        userResponse1.setRole(role);
        signUpUser1.setDate(new Date());
        customerRepository.save(signUpUser1);
        return userResponse1;
    }

    @VisibleForTesting
    public String password(String password) {
        return PasswordUtils.encryptPassword(password);
    }

    @Override
    public CustomerResponse login(CustomerLoginAddRequest customerLoginAddRequest) throws InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException {
        Customer signUpUser = getUserByEmail(customerLoginAddRequest.getEmail());
        String userPassworod = signUpUser.getPassword();
        CustomerResponse signUpResponse = modelMapper.map(signUpUser, CustomerResponse.class);
        boolean passwords = passwordUtils.isPasswordAuthenticated(customerLoginAddRequest.getPassword(), userPassworod, PasswordEncryptionType.BCRYPT);
        if (passwords) {
            modelMapper.map(signUpResponse, Customer.class);
        } else {
            throw new InvalidRequestException(MessageConstant.INCORRECT_PASSWORD);
        }
        signUpUser.setDate(new Date());
        return signUpResponse;
    }

    @Override
    public Page<Customer> getAllCustomerByPagination(CustomerFilter filter, FilterSortRequest.SortRequest<CustomerSortBy> sort, PageRequest pagination) {
        return customerRepository.getAllCustomerByPagination(filter, sort, pagination);
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        Customer customer = getById(id);
        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
        return customerResponse;
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customerResponses=customerRepository.findAllBySoftDeleteFalse();
        List<CustomerResponse> list= new ArrayList<>();
        customerResponses.forEach(customer -> {
            CustomerResponse customerResponse= modelMapper.map(customer,CustomerResponse.class);
            list.add(customerResponse);
        });

        return list;
    }

    @Override
    public Object deleteCustomer(String id) {
        Customer customer= getById(id);
        customer.setSoftDelete(true);
        customerRepository.save(customer);
        return null;
    }


    public void checkValidation(CustomerAddRequest customerAddRequest) {
        if (!customerAddRequest.getPassword().equals(customerAddRequest.getConfirmPassword())) {
            throw new InvalidRequestException(MessageConstant.INCORRECT_PASSWORD);
        }
        if (!customerAddRequest.getContact().matches("^\\d{10}$")) {
            throw new NotFoundException(MessageConstant.INVALID_PHONE_NUMBER);
        }
        if (customerAddRequest.getName().isEmpty()) {
            throw new InvalidRequestException(MessageConstant.NAME_MUST_NOT_BE_NULL);
        }
        if (customerAddRequest.getUserName().isEmpty()) {
            throw new InvalidRequestException(MessageConstant.USERNAME_MUST_NOT_BE_NULL);
        }
        if (customerRepository.existsByEmailAndSoftDeleteIsFalse(customerAddRequest.getEmail())) {
            throw new InvalidRequestException(MessageConstant.EMAIL_ALREADY_EXIST);
        }
    }

    public Customer getUserByEmail(String email) {
        return customerRepository.findUserByEmailAndSoftDeleteIsFalse(email).orElseThrow(() -> new NotFoundException(MessageConstant.USER_NOT_FOUND));
    }

    public Customer getById(String id) {
        return customerRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_NOT_FOUND));

    }

}
