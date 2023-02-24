package com.example.auth.stockPile.service;


import com.example.auth.stockPile.decorator.TopicAddRequest;
import com.example.auth.stockPile.decorator.TopicResponse;

import java.util.List;

public interface TopicService {

    TopicResponse addTopic( String stockId, String userId,TopicAddRequest topicAddRequest);

    TopicResponse getTopicById(String id);

    List<TopicResponse> getAllTopic();

    void deleteTopicById(String id);

    void updateTopic(String id, TopicAddRequest topicAddRequest) throws NoSuchFieldException, IllegalAccessException;
}
