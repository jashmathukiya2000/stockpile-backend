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
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class UserModelServiceImpl implements UserModelService {
    private final UserModelRepository userModelRepository;
    private final ModelMapper modelMapper;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    public UserModelServiceImpl(UserModelRepository userModelRepository, ModelMapper modelMapper, NullAwareBeanUtilsBean nullAwareBeanUtilsBean) {
        this.userModelRepository = userModelRepository;
        this.modelMapper = modelMapper;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
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
            String password = PasswordUtils.encryptPassword(signUpUser1.getPassword());
            signUpUser1.setPassword(password);
            userResponse1.setPassword(password);
        }
        signUpUser1.setRole(role);
        userResponse1.setRole(role);
        userModelRepository.save(signUpUser1);
        return userResponse1;
    }

    @SneakyThrows
    @Override
    public SignUpResponse login(LoginAddRequest loginAddRequest) throws InvocationTargetException, IllegalAccessException {
        UserModel signUpUser = getUserByEmail(loginAddRequest.getEmail());
        String userPasswrod = signUpUser.getPassword();
        SignUpResponse signUpResponse = modelMapper.map(loginAddRequest, SignUpResponse.class);
        boolean passwords = PasswordUtils.isPasswordAuthenticated(loginAddRequest.getPassword(), userPasswrod, PasswordEncryptionType.BCRYPT);
        if (passwords) {
//            nullAwareBeanUtilsBean.copyProperties(signUpResponse, signUpUser);
            modelMapper.map(signUpUser,SignUpResponse.class);
        } else {
            throw new InvalidRequestException(MessageConstant.INCORRECT_PASSWORD);
        }
        return signUpResponse;
    }

    @SneakyThrows
    public UserModel getUserByEmail(String email) throws NotFoundException {
        return userModelRepository.findUserByEmailAndSoftDeleteIsFalse(email).orElseThrow(() -> new NotFoundException(MessageConstant.USER_NOT_FOUND));
    }

}
