package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpAddRequest {
    String name;
    String email;
    String contact;
    String userName;
    String password;
    String confirmPassword;
}
