package com.example.auth.decorator.userModel;

import com.example.auth.commons.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    String name;
    String email;
    String contact;
    String userName;
    Role role;
    @JsonIgnore
    boolean login = false;
}
