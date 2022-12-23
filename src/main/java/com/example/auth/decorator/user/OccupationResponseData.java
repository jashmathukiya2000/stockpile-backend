package com.example.auth.decorator.user;

import com.amazonaws.services.dynamodbv2.xspec.S;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OccupationResponseData {
    String _id;
    String name;
    String occupation;
    int age;
    String email;
    int semester;
    int spi;
}
