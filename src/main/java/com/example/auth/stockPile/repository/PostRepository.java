package com.example.auth.stockPile.repository;

import com.example.auth.stockPile.decorator.PostResponse;
import com.example.auth.stockPile.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String>, PostCustomRepository {
    Optional<Post> findByIdAndSoftDeleteIsFalse(String id);

    List<Post> findAllBySoftDeleteFalse();

    boolean existsByTopicInfoAndSoftDeleteFalse(String topicId);

    List<Post> findByTopicInfoAndSoftDeleteIsFalse(String topicId);

}
