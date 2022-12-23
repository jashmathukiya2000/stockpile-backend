package com.example.auth.decorator.user;

import com.example.auth.decorator.user.Result;
import com.example.auth.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse {
    String id;
    String name;
    String age;
    String occupation;
    String salary;
    Address address;
    String email;
    String phoneNumber;
    @JsonIgnore
    boolean softDelete = false;
    List<Result> result;

}
