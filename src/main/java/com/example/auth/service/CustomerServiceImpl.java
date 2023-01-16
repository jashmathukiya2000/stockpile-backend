package com.example.auth.service;

import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.enums.PasswordEncryptionType;
import com.example.auth.commons.enums.Role;
import com.example.auth.commons.exception.InvalidRequestException;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.utils.PasswordUtils;
import com.example.auth.decorator.customer.CustomerLoginAddRequest;
import com.example.auth.decorator.customer.CustomerSignupAddRequest;
import com.example.auth.decorator.customer.CustomerSignupResponse;
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
import java.util.Date;

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
    public CustomerSignupResponse addCustomer(CustomerSignupAddRequest customerSignupAddRequest, Role role) {
        Customer signUpUser1 = modelMapper.map(customerSignupAddRequest, Customer.class);
        CustomerSignupResponse userResponse1 = modelMapper.map(customerSignupAddRequest, CustomerSignupResponse.class);
        if (signUpUser1.getPassword() != null) {
            String password = password(signUpUser1.getPassword());
            signUpUser1.setPassword(password);
            userResponse1.setPassword(password);
        }
        checkValidation(customerSignupAddRequest);
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
    public CustomerSignupResponse login(CustomerLoginAddRequest customerLoginAddRequest) throws InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException {
        Customer signUpUser = getUserByEmail(customerLoginAddRequest.getEmail());
        String userPassworod = signUpUser.getPassword();
        CustomerSignupResponse signUpResponse = modelMapper.map(signUpUser, CustomerSignupResponse.class);
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


    public void checkValidation(CustomerSignupAddRequest customerSignupAddRequest) {
        if (!customerSignupAddRequest.getPassword().equals(customerSignupAddRequest.getConfirmPassword())) {
            throw new InvalidRequestException(MessageConstant.INCORRECT_PASSWORD);
        }
        if (!customerSignupAddRequest.getContact().matches("^\\d{10}$")) {
            throw new NotFoundException(MessageConstant.INVALID_PHONE_NUMBER);
        }
        if (customerSignupAddRequest.getName().isEmpty()) {
            throw new InvalidRequestException(MessageConstant.NAME_MUST_NOT_BE_NULL);
        }
        if (customerSignupAddRequest.getUserName().isEmpty()) {
            throw new InvalidRequestException(MessageConstant.USERNAME_MUST_NOT_BE_NULL);
        }
        if (customerRepository.existsByEmailAndSoftDeleteIsFalse(customerSignupAddRequest.getEmail())) {
            throw new InvalidRequestException(MessageConstant.EMAIL_ALREADY_EXIST);
        }
    }

    public Customer getUserByEmail(String email) {
        return customerRepository.findUserByEmailAndSoftDeleteIsFalse(email).orElseThrow(() -> new NotFoundException(MessageConstant.USER_NOT_FOUND));
    }

}
