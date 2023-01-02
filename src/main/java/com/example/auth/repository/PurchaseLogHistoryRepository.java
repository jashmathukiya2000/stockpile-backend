package com.example.auth.repository;

import com.example.auth.model.PurchaseLogHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PurchaseLogHistoryRepository extends MongoRepository<PurchaseLogHistory, String> {
    Optional<PurchaseLogHistory> findByIdAndSoftDeleteIsFalse(String id);
}
