package com.example.auth.exercise.decorator;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    String id;

    String firstName;

    String lastName;

    Date birthDate;

    String email;

    String city;

    String state;

    String country;

    @JsonIgnore

    boolean softDelete;

}
