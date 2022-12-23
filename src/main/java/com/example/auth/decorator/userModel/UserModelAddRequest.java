package com.example.auth.decorator.userModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModelAddRequest {
    String name;
    String email;
    String contact;
    String userName;
    String password;
    String confirmPassword;
}
