package com.example.auth.decorator.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
@Getter
@NoArgsConstructor
public enum CategorySortBy {
    ITEM_NAME("itemName"),

    QUANTITY("quantity"),

    PRICE("price");
    @JsonIgnore
      private String value;
    CategorySortBy(String value) {
        this.value=value;

    }
    Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("value", this.toString());
        return map;
    }
}
