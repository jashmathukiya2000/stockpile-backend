package com.example.auth.model;

import com.amazonaws.services.devicefarm.model.ScheduleRunTest;
import com.example.auth.decorator.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "auth")
public class User  {
    String id;
    String name;
    String age;
    String occupation;
String salary;
   Address address;
   String email;
   String phoneNumber;
}
