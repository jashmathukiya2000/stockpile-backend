package com.example.auth.decorator;

import com.example.auth.commons.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseLogAggregationResponse {
    String customerName;

    String itemName;

    double price;

    int quantity;

    double discountInPercent;

    double discountInRupee;

    double totalPrice;


}
