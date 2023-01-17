package com.example.auth.decorator.customer;

import com.example.auth.commons.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    Role role;
    String name;
    String userName;
    String contact;
    String email;
    String password;
    Date date;
    @JsonIgnore
    boolean login = false;


}
