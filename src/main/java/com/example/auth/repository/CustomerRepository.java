package com.example.auth.repository;


import com.example.auth.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String>, CustomerCustomRepository {
    Optional<Customer> findUserByEmailAndSoftDeleteIsFalse(String email);

    boolean existsByEmailAndSoftDeleteIsFalse(String email);

    Optional<Customer> findByIdAndSoftDeleteIsFalse(String id);

      List<Customer> findAllBySoftDeleteFalse();


}
