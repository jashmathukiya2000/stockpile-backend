package com.example.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseLogHistory {
    @Id
    String id;

    String customerId;

    String itemId;

    String itemName;

     double price;

      int quantity;

    double discountInPercent;

    double discountInRupee;

    double totalPrice;

      double sum;


      Date date;

    @JsonIgnore
    boolean softDelete;

}
