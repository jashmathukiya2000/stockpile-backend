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
@Document(collection = "category")
@Builder
public class Category {
    @Id
    String id;
    String itemName;
    int quantity;
    int price;
    @JsonIgnore

    boolean softDelete=false;
}
