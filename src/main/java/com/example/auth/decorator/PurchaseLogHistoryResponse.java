package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchaseLogHistoryResponse  {
    String id;
    String customerId;
    String customerName;

    String itemName;

    double price;

    double discountInPercent;

    double discountInRupee;

    double totalPrice;

    int quantity;


    Date date;
}
