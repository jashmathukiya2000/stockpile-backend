package com.example.auth.stockPile.service;

import com.example.auth.stockPile.model.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessagingException;

public interface FireBaseMessagingService {

    public String sendNotificationByToken(NotificationMessage notificationMessage);

}
