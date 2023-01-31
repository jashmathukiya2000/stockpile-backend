package com.example.auth.model;

import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.user.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "auth")
@Builder
public class User {
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
    String otp;
    @JsonIgnore
    boolean softDelete = false;
    List<Result> result;
    double cgpa;


}
