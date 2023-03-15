package com.example.auth.stockPile.service;


import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.InvalidRequestException;
import com.example.auth.stockPile.model.NotificationMessage;
import com.example.auth.stockPile.repository.NotificationRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FireBaseMessagingServiceImpl implements FireBaseMessagingService {
    private final FirebaseMessaging firebaseMessaging;
private final NotificationRepository notificationRepository;
    public FireBaseMessagingServiceImpl(FirebaseMessaging firebaseMessaging, NotificationRepository notificationRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public String sendNotificationByToken(NotificationMessage notificationMessage) {
        Notification notification= Notification.builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .build();
        Message message=Message.builder()
                .setToken(notificationMessage.getToken())
                .setNotification(notification)
                .build();
      try{
          firebaseMessaging.send(message);
          return "success";
      }catch (FirebaseMessagingException e){
          e.printStackTrace();
          return "error sending notification";
      }


    }
}

