package com.example.auth.decorator.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSpiData {
    String _id;
    String firstName;
    String email;
    String semester;



}
