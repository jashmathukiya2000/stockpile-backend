package com.example.auth.stockPile.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notification")
public class NotificationMessage {
    @Id
    String id;
    String token;
    String body;
    String title;

}
