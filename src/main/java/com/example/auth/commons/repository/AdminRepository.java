package com.example.auth.commons.repository;

import com.example.auth.commons.model.AdminConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends MongoRepository<AdminConfiguration,String> {
}
