package com.example.auth.stockPile.service;

import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.stockPile.decorator.*;
import com.example.auth.stockPile.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PostService {

    PostResponse addPost(PostAddParameter postAddParameter);

    void updatePost(String id, PostAddRequest postAddRequest) throws NoSuchFieldException, IllegalAccessException;

    PostResponse getPostById(String id);

    List<PostResponse> getAllPost();

    void deletePostById(String id);

    Page<PostResponse> getAllPostByPagination(PostFilter filter, FilterSortRequest.SortRequest<PostSortBy> sort, PageRequest pagination);

    List<Post> getAllPostByTopicId(String topicId);
}
