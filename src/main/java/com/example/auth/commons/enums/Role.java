package com.example.auth.commons.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public enum Role {
    ADMIN("Admin"),
    USER("User"),
    MANAGER("Manager");

    private String value;
    Role(String value){
        this.value=value;
    }
}
