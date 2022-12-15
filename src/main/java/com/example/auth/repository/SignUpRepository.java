package com.example.auth.repository;


import com.example.auth.model.SignUpUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SignUpRepository extends MongoRepository<SignUpUser,String> {
    Optional<SignUpUser> findUserByEmailAndSoftDeleteIsFalse(String email);
}
