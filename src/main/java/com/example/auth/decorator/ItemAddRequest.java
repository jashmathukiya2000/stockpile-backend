package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemAddRequest {
    String itemName;

     double price;

     int quantity;

    double discountInPercent;



}