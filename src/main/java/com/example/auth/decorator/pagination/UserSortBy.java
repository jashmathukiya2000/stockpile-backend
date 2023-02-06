package com.example.auth.decorator.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public enum UserSortBy {
    AGE("Age"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    EMAIL("email"),
    SPI("spi");


    @JsonIgnore
    private String value;

    UserSortBy(String value) {
        this.value = value;

    }

    Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("value", this.toString());
        return map;
    }
}
