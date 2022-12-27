package com.example.auth.decorator.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaxSpiData {
       String name;
       String maxSpi;
       String minSpi;
}