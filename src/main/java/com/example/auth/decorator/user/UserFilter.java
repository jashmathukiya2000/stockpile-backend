package com.example.auth.decorator.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilter {
    String name;
    int  age;
    String occupation;
    int  salary;
    List<Result> result;



}
