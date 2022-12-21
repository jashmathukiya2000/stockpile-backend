package com.example.auth.helper;

import com.example.auth.decorator.UserAddRequest;
import com.example.auth.decorator.UserAggregationResponse;
import com.example.auth.decorator.UserFilter;
import com.example.auth.decorator.UserResponse;
import com.example.auth.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;

public class UserServiceImplTestGenerator {


    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static UserAddRequest getMockUserAddRequest() {
        return UserAddRequest.builder()
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

    public static UserResponse getMockUserResponse() {
        return UserResponse.builder()
                .firstName("sans")
                .middleName("km")
                .lastName("shukla")
                .occupation("java developer")
                .email("sanskrityshukla4@gmail.com")
                .age(21)
                .fullName("sans km shukla")
                .phoneNumber("6386580393")
                .salary(25000)
                .build();

    }

    public static User getMockUser() {
        return User.builder()
                .firstName("sans")
                .middleName("km")
                .lastName("shukla")
                .occupation("java developer")
                .email("sanskrityshukla4@gmail.com")
                .age(21)
                .phoneNumber("6386580393")
                .salary(25000)
                .softDelete(false)
                .build();
    }

    public static List<User> getMockUsers() {
        return List.of(User.builder()
                .firstName("sans")
                .middleName("km")
                .lastName("shukla")
                .occupation("java developer")
                .email("sanskrityshukla4@gmail.com")
                .softDelete(false)
                .build());
    }

    public static List<UserResponse> getMockResponse() {
        return List.of(UserResponse.builder()
                .firstName("sans")
                .middleName("km")
                .lastName("shukla")
                .occupation("java developer")
                .email("sanskrityshukla4@gmail.com")
                .build());
    }

    public static UserFilter getMockUserFilter(){
        return UserFilter.builder()
                .age(23)
                .name("sans")
                .build();
    }
    public static List<UserAggregationResponse> getMockAggregationFilter(){
        return List.of(UserAggregationResponse.builder()
                .count(5)
                .name("sans")
                .build());
    }
}
