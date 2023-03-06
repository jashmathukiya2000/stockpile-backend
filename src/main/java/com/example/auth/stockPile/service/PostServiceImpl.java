package com.example.auth.stockPile.service;


import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.stockPile.decorator.*;
import com.example.auth.stockPile.model.*;
import com.example.auth.stockPile.repository.PostRepository;
import com.example.auth.stockPile.repository.ReactionRepository;
import com.example.auth.stockPile.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    private final Reaction reaction;
    private  final ReactionRepository reactionRepository;


    public PostServiceImpl(PostRepository postRepository, StockServiceImpl stockService, TopicServiceImpl topicService, TopicRepository topicRepository, UserDataServiceImpl userDataService, ModelMapper modelMapper, UserHelper userHelper, Reaction reaction, ReactionRepository reactionRepository) {
        this.postRepository = postRepository;
        this.stockService = stockService;
        this.topicService = topicService;
        this.topicRepository = topicRepository;
        this.userDataService = userDataService;
        this.modelMapper = modelMapper;
        this.userHelper = userHelper;

        this.reaction = reaction;
        this.reactionRepository = reactionRepository;
    }


    @Override
    public PostResponse addPost(PostAddParameter postAddParameter) {
        Stock stock = stockService.stockById(postAddParameter.getStockId());
        UserData userData = userDataService.userById(postAddParameter.getUserId());
        Topic topic = topicService.topicById(postAddParameter.getTopicId());
        Post post = modelMapper.map(postAddParameter.getPostAddRequest(), Post.class);
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

    @Override
    public String addReaction(ReactionType reactionType, ReactionAddRequest reactionAddRequest) {
        Post post = getById(reactionAddRequest.getPostId());
        UserData userData = userDataService.userById(reactionAddRequest.getUserId());

        Reaction reaction = new Reaction(); // create a new reaction object
        reaction.setPostId(post.getId());
        reaction.setUserId(userData.getId());
        reaction.setReactionType(reactionType);
        reactionRepository.save(reaction);

        int count = post.getReaction().getOrDefault(reactionType, 0);
        count++;
        post.getReaction().put(reactionType, count);
        postRepository.save(post);

        return null;
    }

//    @Override
//    public ReactionResponse allReactionByPost(String postId) {
//        Post post = getById(postId);
//        ReactionResponse reactionResponse= new ReactionResponse();
//
//    }


    private void update(PostAddRequest postAddRequest, String id) {
        Post post = getById(id);
        if (postAddRequest.getTemplateContent() != null) {
            post.setTemplateContent(postAddRequest.getTemplateContent());

        }
        postRepository.save(post);
    }
    public Post getById(String id) {
        return postRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }

}
