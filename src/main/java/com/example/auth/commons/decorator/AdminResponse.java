package com.example.auth.commons.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
    String from;
    String username;
    String password;

}