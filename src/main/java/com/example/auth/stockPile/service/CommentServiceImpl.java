package com.example.auth.stockPile.service;


import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.stockPile.decorator.CommentAddRequest;
import com.example.auth.stockPile.decorator.CommentResponse;
import com.example.auth.stockPile.model.Comment;
import com.example.auth.stockPile.model.Post;
import com.example.auth.stockPile.model.UserData;
import com.example.auth.stockPile.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final UserHelper userHelper;
    private final ModelMapper modelMapper;
    private  final UserDataServiceImpl userDataService;
    private final PostServiceImpl postService;

    public CommentServiceImpl(CommentRepository commentRepository, UserHelper userHelper, ModelMapper modelMapper, UserDataServiceImpl userDataService, PostServiceImpl postService) {
        this.commentRepository = commentRepository;
        this.userHelper = userHelper;
        this.modelMapper = modelMapper;
        this.userDataService = userDataService;
        this.postService = postService;
    }



    @Override
    public CommentResponse addComment(String userId, String postId, CommentAddRequest commentAddRequest) {
        UserData userData=userDataService.userById(userId);
        Post post= postService.getById(postId);
         Comment comment= modelMapper.map(commentAddRequest,Comment.class);
         comment.setCommentId(userData.getId());
         comment.setCommentedBy(userData.getName());
         comment.setCreatedOn(new Date());
          comment.setPost(post.getId());
          commentRepository.save(comment);
          CommentResponse commentResponse= modelMapper.map(comment,CommentResponse.class);
          return commentResponse;

    }


    @Override
    public void updateComment(String id, CommentAddRequest commentAddRequest) throws NoSuchFieldException, IllegalAccessException {
        Comment comment= getById(id);
        update(commentAddRequest,id);
        userHelper.difference(commentAddRequest,comment);

    }


    @Override
    public CommentResponse getCommentById(String id) {
        Comment comment= getById(id);
        CommentResponse commentResponse= modelMapper.map(comment,CommentResponse.class);
        return commentResponse;
    }

    @Override
    public List<CommentResponse> getAllComment() {
        List<Comment> comments= commentRepository.findAllBySoftDeleteFalse();
        List<CommentResponse> commentResponses= new ArrayList<>();
        comments.forEach(comment -> {
            CommentResponse commentResponse= modelMapper.map(comment,CommentResponse.class);
            commentResponses.add(commentResponse);
        });

        return commentResponses;
    }



    @Override
    public void deleteCommentById(String id) {
        Comment comment= getById(id);
        comment.setSoftDelete(true);
        commentRepository.save(comment);

    }
    private void update(CommentAddRequest commentAddRequest, String id) {

        Comment comment= getById(id);
        if (commentAddRequest.getComment()!=null){
            comment.setComment(commentAddRequest.getComment());
        }
        commentRepository.save(comment);
    }




    public Comment getById(String id){
        return commentRepository.getByIdAndSoftDeleteIsFalse(id).orElseThrow(()->new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }
}
