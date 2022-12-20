package com.example.auth.helper;

import com.example.auth.decorator.Result;
import com.example.auth.decorator.UserResponse;
import com.example.auth.decorator.UserSpiResponse;
import com.example.auth.model.User;

import java.util.Date;
import java.util.List;

public class ResultServiceImplTestGenerator {
    private static final String id="id";

public static UserResponse  MockGetUserResponse( ){
    return UserResponse.builder()
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
public static Result MockAddResult(){
     return Result.builder()
              .spi(7.6)
             .semester(5)
              .build();
}

    public static User getMockUser(){
        return User.builder()
                .firstName("sans")
                .middleName("km")
                .lastName("shukla")
                .occupation("java developer")
                .email("sanskrityshukla4@gmail.com")
                .age(21)
                .phoneNumber("6386580393")
                .salary(25000)
                .build();
    }

    public static List<Result> MockListResult(){
        return List.of(Result.builder()
                .spi(7.6)
                .semester(5)
                .build());
    }

}
