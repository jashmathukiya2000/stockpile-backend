package com.example.auth.controller;
import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.DataResponse;
import com.example.auth.commons.decorator.GeneralHelper;
import com.example.auth.commons.decorator.ListResponse;
import com.example.auth.commons.decorator.Response;
import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PageResponse;
import com.example.auth.stockPile.decorator.*;
import com.example.auth.stockPile.model.Post;
import com.example.auth.stockPile.model.ReactionType;
import com.example.auth.stockPile.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import javax.mail.Multipart;

@RestController
@RequestMapping("post")
public class PostController {
    private final PostService postService;
    private final GeneralHelper generalHelper;


    public PostController(PostService postService, GeneralHelper generalHelper) {
        this.postService = postService;
        this.generalHelper = generalHelper;
    }


    @RequestMapping(name = "addPost",value = "/add",method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<PostResponse> addPost(@RequestBody PostAddParameter postAddParameter){
        DataResponse<PostResponse> dataResponse= new DataResponse<>();
        dataResponse.setData(postService.addPost(postAddParameter));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }
    @RequestMapping(name = "updatePost",value = "/update/post",method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Object> updatePost(@RequestParam String id, @RequestBody PostAddRequest postAddRequest) throws NoSuchFieldException, IllegalAccessException {
        DataResponse<Object> dataResponse= new DataResponse<>();
        postService.updatePost(id,postAddRequest);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.UPDATED_SUCCESSFULLY));
        return dataResponse;
    }
    @RequestMapping(name = "getPostById",value = "/{id}",method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<PostResponse> getPostById( @PathVariable String id){
        DataResponse<PostResponse> dataResponse= new DataResponse<>();
        dataResponse.setData(postService.getPostById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }
    @RequestMapping(name = "getAllPost",value = "/all/post",method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public ListResponse<PostResponse> getAllPost(){
        ListResponse<PostResponse> listResponse= new ListResponse<>();
        listResponse.setData(postService.getAllPost());
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }

    @RequestMapping(name = "deletePostById",value = "/delete/{id}",method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Object> deletePostById( @PathVariable String id){
        DataResponse<Object> dataResponse= new DataResponse<>();
        postService.deletePostById(id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));
        return dataResponse;
    }


   @RequestMapping(name = "addReaction",value = "/add/reaction",method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<String> addReaction(@RequestParam ReactionType reactionType, @RequestBody  ReactionAddRequest reactionAddRequest){
        DataResponse<String> dataResponse= new DataResponse<>();
        dataResponse.setData(postService.addReaction(reactionType,reactionAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.REACTION_ADDED_SUCCESSFULLY));
        return dataResponse;
    }


//      @RequestMapping(name = "allReactionByPost",value = "/all/reaction",method = RequestMethod.POST)
//    @Access(levels = Role.ANONYMOUS)
//    public DataResponse<ReactionResponse> allReaction(@RequestParam String postId){
//        DataResponse<ReactionResponse> dataResponse= new DataResponse<>();
//        dataResponse.setData(postService.allReactionByPost(postId));
//        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
//        return dataResponse;
//    }




    @RequestMapping(name = "getAllPostByPagination",value = "/get/all/pagination",method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public PageResponse<PostResponse> getAllPostByPagination(@RequestBody FilterSortRequest<PostFilter, PostSortBy> filterSortRequest){
        PageResponse<PostResponse> pageResponse= new PageResponse<>();
        Page<PostResponse> page= postService.getAllPostByPagination(filterSortRequest.getFilter(),filterSortRequest.getSort()
                ,generalHelper.getPagination(filterSortRequest.getPagination().getPage(),filterSortRequest.getPagination().getLimit()));
        pageResponse.setData(page);
        pageResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return  pageResponse;

    }



    @RequestMapping(name = "getAllPostByTopicId",value = "/get/all/topicId",method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public ListResponse<Post> getAllPostByTopicId(@RequestParam String topicId){
        ListResponse<Post> listResponse= new ListResponse<>();
        listResponse.setData(postService.getAllPostByTopicId(topicId));
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }


}
