package com.example.auth.repository;

import com.example.auth.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> getByIdAndSoftDeleteIsFalse(String id);
    List<Category> findAllBySoftDeleteFalse();
}
