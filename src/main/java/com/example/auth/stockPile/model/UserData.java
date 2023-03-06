package com.example.auth.stockPile.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor

@NoArgsConstructor

@Document(collection = "userData")
public class UserData {

    @Id

    String id;

    String name;

    String userName;

    String email;

    String contact;

    boolean subscribe;

    @JsonIgnore

    boolean softDelete;
}
