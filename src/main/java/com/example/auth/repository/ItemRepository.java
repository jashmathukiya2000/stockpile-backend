package com.example.auth.repository;

import com.example.auth.model.Category;
import com.example.auth.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item,String>,ItemCustomRepository {
    Optional<Item> findByIdAndSoftDeleteIsFalse(String id);
    List<Item> findAllBySoftDeleteFalse();

}
