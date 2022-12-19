package com.example.auth.helper;

import com.example.auth.common.config.enums.Role;
import com.example.auth.decorator.LoginAddRequest;
import com.example.auth.decorator.SignUpAddRequest;
import com.example.auth.decorator.SignUpResponse;
import com.example.auth.model.UserModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class UserModelServiceTestGenerator {



  public  static ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static SignUpAddRequest getMockSignUpAddRequest(){
      return SignUpAddRequest.builder()
              .password("deny")
              .confirmPassword("deny")
              .contact("2423435")
              .userName("sanskriti")
              .email("deny@gmail.com")
              .name("sans")
              .build();
    }
    public static SignUpResponse getMockSignUpResponse(){
      return SignUpResponse.builder()
              .password("deny")
              .contact("2423435")
              .userName("sanskriti")
              .email("deny@gmail.com")
              .name("sans")
              .role(Role.USER)
            .build();

    }

    public static LoginAddRequest getMockLogin(){
      return LoginAddRequest
              .builder()
              .email("deny@gmail.com")
              .password("deny")
              .build();

    }
    public static UserModel getMockUserModel(){
      return UserModel.builder()
               .password("deny")
                .contact("2423435")
                .userName("sanskriti")
                .email("deny@gmail.com")
                .name("sans")
                .role(Role.USER)
              .softDelete(false)
              .build();
    }


}
