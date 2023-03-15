package com.example.auth.stockPile.service;

import com.example.auth.stockPile.model.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

//@Service
//public class PushNotificationService {
//    private Logger logger= LoggerFactory.getLogger(PushNotificationService.class);
//    private final FCMService fcmService;
//
//    public PushNotificationService(FCMService fcmService) {
//        this.fcmService = fcmService;
//    }
//
//    public void sendPushNotificationToToken(PushNotificationAddRequest request){
//        try {
//            fcmService.sendMessageToToken(request);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//    }
//}

@Service
public class PushNotificationService {

    private final FirebaseMessaging firebaseMessaging;

    public PushNotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void sendNotification(NotificationMessage notification) throws FirebaseMessagingException {

        // Create a Message object with the notification data
        Message message = Message.builder()
                .setToken(notification.getToken())
                .setNotification(Notification.builder()
                        .setTitle(notification.getTitle())
                        .setBody(notification.getBody())
                        .build())
                .build();

        // Send the message
        String response = firebaseMessaging.send(message);

        System.out.println("Successfully sent message: " + response);
    }
}
