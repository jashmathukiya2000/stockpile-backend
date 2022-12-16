package com.example.auth.repository;


import com.example.auth.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserModelRepository extends MongoRepository<UserModel,String> {
    Optional<UserModel> findUserByEmailAndSoftDeleteIsFalse(String email);
}
