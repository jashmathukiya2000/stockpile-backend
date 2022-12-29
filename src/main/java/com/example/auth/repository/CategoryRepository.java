package com.example.auth.repository;
import com.example.auth.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends MongoRepository<Category, String>,CategoryCustomRepository  {
    Optional<Category> findByIdAndSoftDeleteIsFalse(String id);
    List<Category> findAllBySoftDeleteFalse();
    boolean existsBycategoryNameAndSoftDeleteIsFalse(String categoryName);

}
