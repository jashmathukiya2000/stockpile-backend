package com.example.auth.decorator.user;

import com.example.auth.decorator.user.Auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAggregationResponse {
    String _id;
    List<Auth> auth;
    int count;
    String name;
    String occupation;


}