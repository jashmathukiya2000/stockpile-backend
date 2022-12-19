package com.example.auth.decorator;

import com.example.auth.common.config.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Info;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponse {
    String name;
    String email;
    String contact;
    String userName;
    String password;
    Role role;
    @JsonIgnore
    boolean login = false;


}
