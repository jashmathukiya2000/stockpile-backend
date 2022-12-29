package com.example.auth.service;

import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.enums.PasswordEncryptionType;
import com.example.auth.commons.enums.Role;
import com.example.auth.commons.exception.InvalidRequestException;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.utils.PasswordUtils;
import com.example.auth.decorator.customer.CustomerAddRequest;
import com.example.auth.decorator.customer.CustomerSignupAddRequest;
import com.example.auth.decorator.customer.CustomerSignupResponse;
import com.example.auth.model.UserModel;
import com.example.auth.repository.CustomerRepository;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
//    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final PasswordUtils passwordUtils;


    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, PasswordUtils passwordUtils) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
//        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
        this.passwordUtils = passwordUtils;
    }
   public ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Override
    public CustomerSignupResponse signUpUser(CustomerSignupAddRequest signUpAddRequest, Role role) {
        UserModel signUpUser1 = modelMapper.map(signUpAddRequest, UserModel.class);
        CustomerSignupResponse userResponse1 = modelMapper.map(signUpAddRequest, CustomerSignupResponse.class);
        if (!signUpAddRequest.getPassword().equals(signUpAddRequest.getConfirmPassword())) {
            throw new InvalidRequestException(MessageConstant.INCORRECT_PASSWORD);
        }
        if (signUpUser1.getPassword() != null) {
            String password = password(signUpUser1.getPassword());
            signUpUser1.setPassword(password);
            userResponse1.setPassword(password);
        }
        signUpUser1.setRole(role);
        userResponse1.setRole(role);
        customerRepository.save(signUpUser1);
        return userResponse1;
    }

    @VisibleForTesting
    public String password(String password) {
        return PasswordUtils.encryptPassword(password);
    }


    @Override
    public CustomerSignupResponse login(CustomerAddRequest customerAddRequest) throws InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException {
        UserModel signUpUser = getUserByEmail(customerAddRequest.getEmail());
        String userPassworod = signUpUser.getPassword();

        CustomerSignupResponse signUpResponse = modelMapper.map(customerAddRequest, CustomerSignupResponse.class);
        boolean passwords = passwordUtils.isPasswordAuthenticated(customerAddRequest.getPassword(),userPassworod, PasswordEncryptionType.BCRYPT);
        if (passwords) {
//            nullAwareBeanUtilsBean.copyProperties(signUpResponse, signUpUser);
            modelMapper.map(signUpUser, CustomerSignupResponse.class);
        } else {
            throw new InvalidRequestException(MessageConstant.INCORRECT_PASSWORD);
        }
        return signUpResponse;
    }


    public UserModel getUserByEmail(String email)  {
        return customerRepository.findUserByEmailAndSoftDeleteIsFalse(email).orElseThrow(() -> new NotFoundException(MessageConstant.USER_NOT_FOUND));
    }


}
