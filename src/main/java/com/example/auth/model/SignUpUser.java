package com.example.auth.model;

import com.example.auth.common.config.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class SignUpUser {
    String name;
    String email;
    String contact;
    String userName;
    String password;
    Role role;
    @JsonIgnore
//    boolean login = false;
    boolean softDelete=false;


}
