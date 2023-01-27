package com.example.auth.repository;

import com.example.auth.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String>, ItemCustomRepository {
    Optional<Item> findByIdAndSoftDeleteIsFalse(String id);

     Item findByItemNameAndSoftDeleteIsFalse(String ItemName);

     List<Item> findBySoftDeleteFalse();

    boolean existsByItemNameAndSoftDeleteIsFalse(String itemName);

    List<Item> findByCategoryIdAndSoftDeleteFalse(String categoryId);
}
