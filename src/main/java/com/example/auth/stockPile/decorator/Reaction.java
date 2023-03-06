package com.example.auth.stockPile.decorator;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {

    @Id
    String id;
    String userId;
    String postId;



}
