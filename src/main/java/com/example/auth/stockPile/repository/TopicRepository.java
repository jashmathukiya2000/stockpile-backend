package com.example.auth.stockPile.repository;

import com.example.auth.stockPile.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TopicRepository extends MongoRepository<Topic,String> ,TopicCustomRepository{
    Optional<Topic> getTopicByIdAndSoftDeleteIsFalse(String id);


   Topic getIdByTitleAndCreatedOnAndSoftDeleteFalse(Date createdOn, String topicTitle );


// boolean existsByTitleAndSoftDeleteFalse(String topicTitle);


    List<Topic> findAllBySoftDeleteFalse();


}
