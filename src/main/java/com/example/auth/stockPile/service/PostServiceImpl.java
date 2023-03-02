package com.example.auth.stockPile.service;


import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.stockPile.decorator.PostAddRequest;
import com.example.auth.stockPile.decorator.PostFilter;
import com.example.auth.stockPile.decorator.PostResponse;
import com.example.auth.stockPile.decorator.PostSortBy;
import com.example.auth.stockPile.model.Post;
import com.example.auth.stockPile.model.Stock;
import com.example.auth.stockPile.model.Topic;
import com.example.auth.stockPile.model.UserData;
import com.example.auth.stockPile.repository.PostRepository;
import com.example.auth.stockPile.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final StockServiceImpl stockService;
    private final TopicServiceImpl topicService;
    private final TopicRepository topicRepository;
    private final UserDataServiceImpl userDataService;
    private final ModelMapper modelMapper;
    private final UserHelper userHelper;

    public PostServiceImpl(PostRepository postRepository, StockServiceImpl stockService, TopicServiceImpl topicService, TopicRepository topicRepository, UserDataServiceImpl userDataService, ModelMapper modelMapper, UserHelper userHelper) {
        this.postRepository = postRepository;
        this.stockService = stockService;
        this.topicService = topicService;
        this.topicRepository = topicRepository;
        this.userDataService = userDataService;
        this.modelMapper = modelMapper;
        this.userHelper = userHelper;
    }


    @Override
    public PostResponse addPost(String stockId, String userId, String topicId, PostAddRequest postAddRequest) {
        Stock stock = stockService.stockById(stockId);
        UserData userData = userDataService.userById(userId);
        Topic topic = topicService.topicById(topicId);
        Post post = modelMapper.map(postAddRequest, Post.class);
        post.setPostBy(userData);
        post.setCreatedOn(new Date());
        post.setStockInfo(stock.getId());
        post.setTopicInfo(topic.getId());
        postRepository.save(post);
        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
        return postResponse;
    }

    @Override
    public void updatePost(String id, PostAddRequest postAddRequest) throws NoSuchFieldException, IllegalAccessException {
        Post post = getById(id);
        update(postAddRequest, id);
        userHelper.difference(postAddRequest, post);

    }


    @Override
    public PostResponse getPostById(String id) {
        Post post = getById(id);
        PostResponse postResponse = modelMapper.map(post, PostResponse.class);

        return postResponse;
    }

    @Override
    public List<PostResponse> getAllPost() {
        List<Post> posts = postRepository.findAllBySoftDeleteFalse();
        List<PostResponse> postResponses = new ArrayList<>();
        posts.forEach(post -> {
            PostResponse postResponse = modelMapper.map(post, PostResponse.class);
            postResponses.add(postResponse);
        });

        return postResponses;
    }

    @Override
    public void deletePostById(String id) {
        Post post = getById(id);
        post.setSoftDelete(true);
        postRepository.save(post);

    }

    @Override
    public Page<PostResponse> getAllPostByPagination(PostFilter filter, FilterSortRequest.SortRequest<PostSortBy> sort, PageRequest pagination) {
        return postRepository.getAllPostByPagination(filter, sort, pagination);
    }

    @Override
    public List<Post> getAllPostByTopicId(String topicId) {
        List<PostResponse> postResponses = new ArrayList<>();
        boolean exist = postRepository.existsByTopicInfoAndSoftDeleteFalse(topicId);
        if (!exist) {
            throw new NotFoundException(MessageConstant.ID_NOT_FOUND);
        } else {
            List<Post> posts = postRepository.findByTopicInfoAndSoftDeleteIsFalse(topicId);
            return posts;
        }

    }


    private void update(PostAddRequest postAddRequest, String id) {
        Post post = getById(id);
        if (postAddRequest.getContent() != null) {
            post.setTemplateContent(postAddRequest.getContent());

        }
        postRepository.save(post);
    }

    public Post getById(String id) {
        return postRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }
}
