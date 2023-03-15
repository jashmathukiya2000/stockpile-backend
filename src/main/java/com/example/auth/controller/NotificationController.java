package com.example.auth.controller;
import com.example.auth.commons.Access;
import com.example.auth.commons.enums.Role;
import com.example.auth.stockPile.decorator.PushNotificationAddRequest;
import com.example.auth.stockPile.decorator.PushNotificationResponse;
import com.example.auth.stockPile.service.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
//  private final   FireBaseMessagingService fireBaseMessagingService;
//
//    public NotificationController(FireBaseMessagingService fireBaseMessagingService) {
//        this.fireBaseMessagingService = fireBaseMessagingService;
//    }
//
//    @RequestMapping(name = "sendNotification",value = "notification",method = RequestMethod.POST)
//    @Access(levels = Role.ANONYMOUS)
//    public String sendNotification(@RequestBody NotificationMessage notificationMessage){
//      String notification=fireBaseMessagingService.sendNotificationByToken(notificationMessage);
//        System.out.println("notification"+notification);
//        return notification;
//    }

  private final PushNotificationService pushNotificationService;

  public NotificationController(PushNotificationService pushNotificationService) {
    this.pushNotificationService = pushNotificationService;
  }

  @RequestMapping(name = "sendNotification",value = "notification",method = RequestMethod.POST)
  @Access(levels = Role.ANONYMOUS)
  public ResponseEntity sendTokenNotification(@RequestBody PushNotificationAddRequest request) {
    pushNotificationService.sendPushNotificationToToken(request);
    System.out.println("princr");
    return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
  }

}
