package com.example.auth.helper;

import com.example.auth.common.config.enums.Role;
import com.example.auth.decorator.SignUpAddRequest;
import com.example.auth.decorator.SignUpResponse;
import com.example.auth.model.UserModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class UserModelServiceTestGenerator {

    private static final String email = "sans@1234gmail.com";

    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static UserModel getMockuserModel() {
        return UserModel.builder()
                .name("sans")
                .email(email)
                .password("password")
                .role(Role.USER)
                .build();
    }

    public static SignUpAddRequest getMockUserAddRequest() {
        return SignUpAddRequest.builder()
                .email(email)
                .password("password")
                .confirmPassword("password")
                .name("xyz")
                .build();

    }

    public static SignUpResponse getMockSignUpResponse(String password) {
        return SignUpResponse.builder()
                .email(email)
                .password(password)
                .name("xyz")
                .role(Role.USER)
                .build();
    }

}
