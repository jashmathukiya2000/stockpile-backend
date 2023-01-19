package com.example.auth.decorator.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public enum PurchaseLogSortBy {
    ITEM_NAME("itemName");
    @JsonIgnore


    private String value;

    PurchaseLogSortBy(String itemName) {
        this.value = itemName;
    }


    Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("value", this.toString());
        return map;
    }
}
