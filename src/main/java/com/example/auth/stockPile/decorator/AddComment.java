package com.example.auth.stockPile.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddComment {
    String userId;
    String postId;
    String comment;

}
