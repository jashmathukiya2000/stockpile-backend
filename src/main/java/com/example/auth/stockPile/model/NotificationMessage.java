package com.example.auth.stockPile.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
public class NotificationMessage {
//    String token;
//    String body;
//    String title;

    private String title;
    private String message;
    private String topic;
    private String token;

}
