package com.example.auth.decorator;

import com.example.auth.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor


@NoArgsConstructor
public class UserAddRequest {
   String firstName;
   String middleName;
   String lastName;
    double age;
    String occupation;
    double salary;
    Address address;
    String email;
    String phoneNumber;
}
