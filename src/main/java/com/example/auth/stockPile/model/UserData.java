package com.example.auth.stockPile.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

@NoArgsConstructor

public class UserData {

    String id;

    String name;

    String userName;

    String email;

    String contact;

    @JsonIgnore

    boolean softDelete;
}
