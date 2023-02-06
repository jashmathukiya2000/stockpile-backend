package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultEmailRequest {
    String fullName;
    String semester;
    String spi;
    String cgpi;
    String email;

}
