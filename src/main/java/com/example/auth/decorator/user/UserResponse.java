package com.example.auth.decorator.user;

import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.user.Result;
import com.example.auth.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    String id;
    String firstName;
    String middleName;
    String lastName;
    String fullName;
    double age;
    String occupation;
    double salary;
    Address address;
    String email;
    String phoneNumber;
    Role role;
    List<Result> result;
    double cgpa;
    String token;

}
