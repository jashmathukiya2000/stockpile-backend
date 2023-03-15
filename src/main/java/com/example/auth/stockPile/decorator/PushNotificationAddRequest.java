package com.example.auth.stockPile.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationAddRequest {
    String token;
    String topic;
    String message;
    String title;
}
