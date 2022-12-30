package com.example.auth.repository;


import com.example.auth.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<UserModel,String> {
    Optional<UserModel> findUserByEmailAndSoftDeleteIsFalse(String email);
      boolean existsByEmailAndSoftDeleteIsFalse(String email);
}
