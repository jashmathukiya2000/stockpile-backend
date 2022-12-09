package com.example.auth.repository;

import com.example.auth.decorator.UserResponse;
import com.example.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String>,UserCustomRepository {
Optional<User> findByIdAndSoftDeleteIsFalse(String id);

}
