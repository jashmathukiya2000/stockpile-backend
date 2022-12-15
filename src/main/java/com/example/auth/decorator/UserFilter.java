package com.example.auth.decorator;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {
    String name;
    int  age;
    String occupation;
    int  salary;
    List<Result> result;



}
