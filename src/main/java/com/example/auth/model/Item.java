package com.example.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "items")
@Builder
public class Item {
    String categoryId;
    String id;
    String itemName;
    double price;
    int quantity;
    @JsonIgnore
    boolean softDelete;

}
