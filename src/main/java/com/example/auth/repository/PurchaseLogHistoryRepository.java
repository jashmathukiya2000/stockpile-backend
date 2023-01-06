package com.example.auth.repository;

import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import com.example.auth.model.PurchaseLogHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PurchaseLogHistoryRepository extends MongoRepository<PurchaseLogHistory, String>,PurchaseLogHistoryCustomRepository {
    Optional<PurchaseLogHistory> findByIdAndSoftDeleteIsFalse(String id);

    List<PurchaseLogHistory> findAllBySoftDeleteFalse();

    List<PurchaseLogHistory> findByCustomerIdAndSoftDeleteFalse(String customerId);



}
