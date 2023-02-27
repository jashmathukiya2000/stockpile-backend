package com.example.auth.exercise.repository;

import com.example.auth.exercise.model.UserInfo;
import com.example.auth.stockPile.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

    Optional<UserInfo> findByIdAndSoftDeleteIsFalse(String id);

            List<UserInfo> findAllBySoftDeleteFalse();

//    List<UserInfo> findByCity(String city);
//
//    List<UserInfo> findByStateAndCity(String state, String city);
////

}
