package com.example.auth.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "items")
@Builder
public class Item {
    @Id
    String id;

    String itemName;

    String categoryId;

    int quantity;

    double price;

    double discountInPercent;

    double discountInRupee;


    double totalPrice;

    Date date;

    @JsonIgnore
    boolean softDelete;
}
