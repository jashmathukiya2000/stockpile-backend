package com.example.auth.decorator;

import com.example.auth.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    String id;
    String name;
    String age;
    String occupation;
    String salary;
    Address address;
}
