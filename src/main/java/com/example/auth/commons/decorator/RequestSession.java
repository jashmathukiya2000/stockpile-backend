package com.example.auth.commons.decorator;


import com.example.auth.commons.JWTUser;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Locale;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class RequestSession {
    JWTUser jwtUser;
    String timezone;
    Locale locale = Locale.ENGLISH;
}
