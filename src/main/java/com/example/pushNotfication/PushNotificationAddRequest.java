package com.example.pushNotfication;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "devices")
public class PushNotificationAddRequest {
          String id;
       private String[] token;
        private String title;
        private String body;

    }
