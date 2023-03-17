package com.example.auth.controller;
import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.DataResponse;
import com.example.auth.commons.decorator.Response;
import com.example.auth.commons.enums.Role;
import com.example.auth.stockPile.decorator.PushNotificationResponse;

import com.example.pushNotfication.FcmService;
import com.example.pushNotfication.PushNotificationAddRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NotificationController {

  private final FcmService fcmService;
  public NotificationController(FcmService fcmService) {
    this.fcmService = fcmService;
  }

@RequestMapping(name = "sendNotification", value = "/push-notification", method = RequestMethod.POST)
@Access(levels = Role.ANONYMOUS)
public DataResponse<Object> sendPushNotification(@RequestBody PushNotificationAddRequest request) {
    DataResponse<Object> dataResponse=new DataResponse<>();
  fcmService.sendPushNotification(request.getToken(), request.getTitle(), request.getBody());
  dataResponse.setStatus(Response.getOkResponse(ResponseConstant.NOTIFICATION_SENT_SUCCESSFULLY));
  return dataResponse;
}


}
