package com.example.auth.stockPile.repository;

import com.example.auth.stockPile.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends MongoRepository<Topic,String> {
    Optional<Topic> getTopicByIdAndSoftDeleteIsFalse(String id);


    List<Topic> findAllBySoftDeleteFalse();


}
