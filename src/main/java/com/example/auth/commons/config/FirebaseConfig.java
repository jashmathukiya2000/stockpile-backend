package com.example.auth.commons.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(
                "C:\\Java\\auth\\src\\main\\resources\\stocks-forum-firebase-adminsdk-se0gx-24bca6a5e5.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(
               serviceAccount
        );
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(options);
        return FirebaseMessaging.getInstance(app);
    }
}