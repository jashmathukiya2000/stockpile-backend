package com.example.auth.service;

import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
import com.example.auth.common.config.constant.MessageConstant;
import com.example.auth.common.config.enums.PasswordEncryptionType;
import com.example.auth.common.config.enums.Role;
import com.example.auth.common.config.exception.InvalidRequestException;
import com.example.auth.common.config.exception.NotFoundException;
import com.example.auth.common.config.utils.PasswordUtils;
import com.example.auth.decorator.LoginAddRequest;
import com.example.auth.decorator.SignUpAddRequest;
import com.example.auth.decorator.SignUpResponse;
import com.example.auth.model.UserModel;
import com.example.auth.repository.UserModelRepository;
import com.google.common.annotations.VisibleForTesting;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class UserModelServiceImpl implements UserModelService {
    private final UserModelRepository userModelRepository;
    private final ModelMapper modelMapper;
//    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final PasswordUtils passwordUtils;


    public UserModelServiceImpl(UserModelRepository userModelRepository, ModelMapper modelMapper, PasswordUtils passwordUtils) {
        this.userModelRepository = userModelRepository;
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
    public SignUpResponse signUpUser(SignUpAddRequest signUpAddRequest, Role role) {
        UserModel signUpUser1 = modelMapper.map(signUpAddRequest, UserModel.class);
        SignUpResponse userResponse1 = modelMapper.map(signUpAddRequest, SignUpResponse.class);
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
        userModelRepository.save(signUpUser1);
        return userResponse1;
    }

    @VisibleForTesting
     String password(String password) {
        return PasswordUtils.encryptPassword(password);
    }


    @Override
    public SignUpResponse login(LoginAddRequest loginAddRequest) throws InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException {
        UserModel signUpUser = getUserByEmail(loginAddRequest.getEmail());
        String userPassworod = signUpUser.getPassword();

        SignUpResponse signUpResponse = modelMapper.map(loginAddRequest, SignUpResponse.class);
        boolean passwords = passwordUtils.isPasswordAuthenticated(loginAddRequest.getPassword(),userPassworod, PasswordEncryptionType.BCRYPT);
        if (passwords) {
//            nullAwareBeanUtilsBean.copyProperties(signUpResponse, signUpUser);
            modelMapper.map(signUpUser,SignUpResponse.class);
        } else {
            throw new InvalidRequestException(MessageConstant.INCORRECT_PASSWORD);
        }
        return signUpResponse;
    }


    public UserModel getUserByEmail(String email)  {
        return userModelRepository.findUserByEmailAndSoftDeleteIsFalse(email).orElseThrow(() -> new NotFoundException(MessageConstant.USER_NOT_FOUND));
    }


}
