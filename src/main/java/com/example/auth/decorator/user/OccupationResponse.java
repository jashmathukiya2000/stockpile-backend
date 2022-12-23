package com.example.auth.decorator.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class OccupationResponse {
    List<OccupationResponseData> auth;
    String name;
    String occupation;
    int count;
}
