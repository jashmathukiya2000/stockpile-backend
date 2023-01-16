package com.example.auth.commons.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter

public enum Role {
    ADMIN("Admin"),
    USER("User"),
    MANAGER("Manager"),
    ANONYMOUS("Anonymous");

    private String value;
    Role(String value) {
        this.value = value;
    }
    public static List<Map<String,String>> toList(){
        return Arrays.stream(values()).map(Role::toMap).collect(Collectors.toList());
    }
    Map<String,String> toMap(){
        Map<String,String> map = new HashMap<>();
        map.put("name",this.value);
        map.put("value",this.toString());
        return map;
    }


}
