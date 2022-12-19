package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserSpiResponse {
    String _id;
    List<UserSpiData> auth;
    int sum;
    int count;
    int average;


}
