package com.example.auth.stockPile.decorator;
import com.example.auth.stockPile.model.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionResponse {
    String userId;
    String name;
   ReactionType reaction;
}
