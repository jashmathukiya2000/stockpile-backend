package com.example.auth.common.config.enums;

import lombok.Builder;
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
