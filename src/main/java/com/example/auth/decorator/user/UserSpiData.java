package com.example.auth.decorator.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSpiData {
    String _id;
    String name;
    String email;
    String semester;


}
