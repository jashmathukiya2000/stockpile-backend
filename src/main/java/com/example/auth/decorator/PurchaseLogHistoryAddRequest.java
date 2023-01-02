package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseLogHistoryAddRequest {
    String itemName;

      double price;

      int quantity;

    double discountInPercent;
}
