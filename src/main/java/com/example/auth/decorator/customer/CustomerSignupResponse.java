package com.example.auth.decorator.customer;

import com.example.auth.commons.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSignupResponse {
    String name;
    String email;
    String contact;
    String userName;
    String password;
    Role role;
    @JsonIgnore
    boolean login = false;


}
