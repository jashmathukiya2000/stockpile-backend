package com.example.auth.stockPile.decorator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public enum StockSortBy {
    NAME("Name"),
    SYMBOL("Symbol");
    
  @JsonIgnore
    private String value;

    StockSortBy(String value) {
        this.value = value;
    }
}
