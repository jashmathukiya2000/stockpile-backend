package com.example.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "auth")
public class User {
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

}
