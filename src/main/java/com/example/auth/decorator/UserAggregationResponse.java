package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAggregationResponse {
    String _id;
    List<Auth> auth;
    String count;
    String name;
    String occupation;
}