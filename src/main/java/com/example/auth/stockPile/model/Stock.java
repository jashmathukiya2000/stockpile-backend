package com.example.auth.stockPile.model;


import com.example.auth.commons.Access;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "stock_info")
public class Stock {

    @Id

    String id;

    String symbol;

    String name;

    String description;

    @JsonIgnore

    boolean softDelete;

}
