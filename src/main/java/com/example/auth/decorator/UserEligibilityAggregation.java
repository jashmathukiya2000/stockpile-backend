package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEligibilityAggregation {
    String _id;
    String firstName;
    double age;
    String Eligibility;

}
