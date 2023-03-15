package com.example.auth.stockPile.repository;

import com.example.auth.stockPile.model.NotificationMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationMessage,String> {

}
