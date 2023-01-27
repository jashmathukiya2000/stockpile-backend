package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchaseLogHistoryAddRequest {
//    String itemName;

//      double price;

      int quantity;

//    double discountInPercent;
}
