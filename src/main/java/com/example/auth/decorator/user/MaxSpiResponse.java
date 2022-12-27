package com.example.auth.decorator.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaxSpiResponse {
    String _id;

    List<MaxSpiData> auth;


}
