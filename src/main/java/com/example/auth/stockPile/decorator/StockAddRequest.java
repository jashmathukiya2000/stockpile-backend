package com.example.auth.stockPile.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockAddRequest {
    String symbol;

    String name;

    List<String> subscribers;

    String description;
}
