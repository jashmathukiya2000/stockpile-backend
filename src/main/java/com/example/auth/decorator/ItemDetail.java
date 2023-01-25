package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDetail {
    Date date;
    double price;
    String itemName;
    double count;
    double totalPrice;
}
