package com.example.auth.decorator;

import com.example.auth.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {
    String name;
    String age;
    String occupation;
    String salary;
    @JsonIgnore
    boolean softDelete = false;

}
