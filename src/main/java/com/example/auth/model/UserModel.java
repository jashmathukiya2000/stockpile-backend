package com.example.auth.model;

import com.example.auth.commons.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
@Builder
public class UserModel {
    Role role;

    String name;

    String userName;

    String contact;

    String email;

    String password;
    Date date;

    @JsonIgnore
    boolean softDelete = false;


}
