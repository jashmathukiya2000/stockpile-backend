//package com.example.auth.stockPile.service;
//
//
//import com.amazonaws.services.codepipeline.model.ActionExecution;
//import com.amazonaws.services.dynamodbv2.xspec.N;
//import com.example.auth.stockPile.decorator.PushNotificationAddRequest;
////import com.google.firebase.messaging.*;
//import com.google.firebase.messaging.*;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.concurrent.ExecutionException;
//
//@Service
//public class FCMService {
//    private Logger logger= LoggerFactory.getLogger(FCMService.class);
//    public void sendMessageToToken(PushNotificationAddRequest request) throws ExecutionException, InterruptedException {
//        Message message=getPreconfiguredMessageToToken(request);
//        Gson gson= new GsonBuilder().setPrettyPrinting().create();
//        String JsonOutput=gson.toJson(message);
//        String response=sendAndGetResponse(message);
//        logger.info("sent message to token, device token:"+request.getToken()+","+response);
//    }
//
//    private String sendAndGetResponse(Message message) throws ExecutionException, InterruptedException {
//        return FirebaseMessaging.getInstance().sendAsync(message).get();
//    }
//
//    private Message getPreconfiguredMessageToToken(PushNotificationAddRequest request) {
//       return   getPreconfiguredMessageBuilder(request).setToken(request.getToken()).build();
//    }
//
//
//
//    private Message getPreconfiguredMessageWithoutData(PushNotificationAddRequest request) {
//        return   getPreconfiguredMessageBuilder(request).setTopic(request.getTopic()).build();
//    }
//
//    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationAddRequest request) {
//
//        AndroidConfig androidConfig=getAndroidConfig(request.getTopic());
//ApnsConfig apnsConfig=getApnsConfig(request.getTopic());
//return Message.builder().setApnsConfig(apnsConfig)
//        .setAndroidConfig(androidConfig).setNotification(new Notification(request.getTitle(),request.getMessage()));
//    }
//
//
//    private AndroidConfig getAndroidConfig(String topic){
//        return AndroidConfig.builder()
//                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
//                .setPriority(AndroidConfig.Priority.HIGH)
//                .setNotification(AndroidNotification.builder()
//                        .setTag(topic).build()).build();
//    }
//    private ApnsConfig getApnsConfig(String topic){
//        return ApnsConfig.builder().setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
//    }
//
//
//}
