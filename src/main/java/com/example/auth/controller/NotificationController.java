package com.example.auth.controller;


import com.example.auth.commons.Access;
import com.example.auth.commons.decorator.DataResponse;
import com.example.auth.commons.decorator.Response;
import com.example.auth.commons.enums.Role;
import com.example.auth.stockPile.model.NotificationMessage;
import com.example.auth.stockPile.service.FireBaseMessagingService;
import com.example.auth.stockPile.service.PushNotificationService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
  private final   FireBaseMessagingService fireBaseMessagingService;

    public NotificationController(FireBaseMessagingService fireBaseMessagingService) {
        this.fireBaseMessagingService = fireBaseMessagingService;
    }

    @RequestMapping(name = "sendNotification",value = "notification",method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public String sendNotification(@RequestBody NotificationMessage notificationMessage){
      String notification=fireBaseMessagingService.sendNotificationByToken(notificationMessage);
        System.out.println("notification"+notification);
        return notification;
    }


}
