package com.example.auth.helper;

import com.example.auth.decorator.SignUpAddRequest;
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
              .build();
    }


}
