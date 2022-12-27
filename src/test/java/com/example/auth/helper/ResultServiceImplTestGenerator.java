package com.example.auth.helper;

import com.example.auth.decorator.user.Result;
import com.example.auth.decorator.user.UserResponse;
import com.example.auth.decorator.user.UserSpiResponse;
import com.example.auth.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;

public class ResultServiceImplTestGenerator {
    private static final String id = "id";


    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static UserResponse MockGetUserResponse() {
        return UserResponse.builder()
                .id(id)
                .firstName("sans")
                .middleName("km")
                .lastName("shukla")
                .fullName("sans km shukla")
                .occupation("java developer")
                .email("sanskrityshukla4@gmail.com")
                .age(21)
                .phoneNumber("6386580393")
                .salary(25000)
                .cgpa(7.6)
                .result(MockListResult())
                .build();


    }

    public static Result MockAddResult() {
        return Result.builder()
                .spi(7.6)
                .semester(5)
                .build();


    }

    public static User getMockUser(List<Result> results) {
        return User.builder()
                .id(id)
                .firstName("sans")
                .middleName("km")
                .lastName("shukla")
                .fullName("sans km shukla")
                .occupation("java developer")
                .email("sanskrityshukla4@gmail.com")
                .age(21)
                .phoneNumber("6386580393")
                .salary(25000)
                .cgpa(7.6)
                .result(results)
                .build();

    }

    public static List<Result> MockListResult() {
        return List.of(Result.builder()
                .spi(7.6)
                .semester(5)
                .build());
    }
    public static List<UserSpiResponse> getMockUserSpiResponse(){
        return  List.of(UserSpiResponse.builder()
                .sum(7)
                .build());
    }

}
