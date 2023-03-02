package com.example.auth.stockPile.service;

import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.stockPile.decorator.PostAddRequest;
import com.example.auth.stockPile.decorator.PostFilter;
import com.example.auth.stockPile.decorator.PostResponse;
import com.example.auth.stockPile.decorator.PostSortBy;
import com.example.auth.stockPile.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PostService {

    PostResponse addPost(String stockId, String userId, String topicId, PostAddRequest postAddRequest);

    void updatePost(String id, PostAddRequest postAddRequest) throws NoSuchFieldException, IllegalAccessException;

    PostResponse getPostById(String id);

    List<PostResponse> getAllPost();

    void deletePostById(String id);

    Page<PostResponse> getAllPostByPagination(PostFilter filter, FilterSortRequest.SortRequest<PostSortBy> sort, PageRequest pagination);

    List<Post> getAllPostByTopicId(String topicId);
}
