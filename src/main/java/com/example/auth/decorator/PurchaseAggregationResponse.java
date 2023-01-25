package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseAggregationResponse {
    String _id;
    List<ItemDetails> itemDetails;
    double totalItem;

}
