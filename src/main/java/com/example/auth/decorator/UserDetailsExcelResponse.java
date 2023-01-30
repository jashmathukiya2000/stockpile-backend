package com.example.auth.decorator;

import com.amazonaws.services.identitymanagement.model.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsExcelResponse {
    String firstName;
    List<UserDetails> userDetails;

}
