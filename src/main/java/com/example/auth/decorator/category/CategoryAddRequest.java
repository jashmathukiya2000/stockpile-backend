package com.example.auth.decorator.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryAddRequest {
    String itemName;
    int quantity;
    int price;
}
