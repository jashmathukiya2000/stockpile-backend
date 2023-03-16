package com.example.auth.controller;
import com.example.auth.commons.Access;
import com.example.auth.commons.enums.Role;
import com.example.auth.stockPile.decorator.PushNotificationAddRequest;
import com.example.auth.stockPile.decorator.PushNotificationResponse;
import com.example.auth.firebase.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {


  private final PushNotificationService pushNotificationService;

  public NotificationController(PushNotificationService pushNotificationService) {
    this.pushNotificationService = pushNotificationService;
  }

  @RequestMapping(name = "sendNotification",value = "notification/token",method = RequestMethod.POST)
  @Access(levels = Role.ANONYMOUS)
  public ResponseEntity sendTokenNotification(@RequestBody PushNotificationAddRequest request) {
    pushNotificationService.sendPushNotificationToToken(request);
    return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
  }




}
