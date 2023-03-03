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
import com.example.auth.stockPile.model.Topic;
import com.example.auth.stockPile.service.TopicService;
import com.google.api.client.util.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("topics")
public class TopicController {

    private final TopicService topicService;
    private final GeneralHelper generalHelper;

    public TopicController(TopicService topicService, GeneralHelper generalHelper) {
        this.topicService = topicService;
        this.generalHelper = generalHelper;
    }

    @RequestMapping(name = "addTopic", value = "/add", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<TopicResponse> addTopic(@RequestParam String stockId, @RequestParam String userId, @RequestBody TopicAddRequest topicAddRequest) {
        DataResponse<TopicResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(topicService.addTopic(stockId, userId, topicAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "updateTopic", value = "/update/topic", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Object> updateTopic(@RequestParam String id, @RequestBody TopicAddRequest topicAddRequest) throws NoSuchFieldException, IllegalAccessException {
        DataResponse<Object> dataResponse = new DataResponse<>();
        topicService.updateTopic(id, topicAddRequest);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.UPDATED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "getTopicById", value = "/{id}", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<TopicResponse> getTopicById(@RequestParam String id) {
        DataResponse<TopicResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(topicService.getTopicById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

    @RequestMapping(name = "getAllTopic", value = "/get/all/topic", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public ListResponse<TopicResponse> getAllTopic() {
        ListResponse<TopicResponse> listResponse = new ListResponse<>();
        listResponse.setData(topicService.getAllTopic());
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }

    @RequestMapping(name = "deleteTopicById", value = "/delete/{id}", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Object> deleteTopicById(@RequestParam String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        topicService.deleteTopicById(id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));
        return dataResponse;
    }

//    @RequestMapping(name = "getTopicIdByTitleAndDate", value = "/topicId/title/date", method = RequestMethod.GET)
//    @Access(levels = Role.ANONYMOUS)
//    public ListResponse<TitleResponse> getTopicIdByTitleAndDate(@RequestParam String  createdOn, @RequestParam String title) throws JSONException {
//        ListResponse<TitleResponse> dataResponse = new ListResponse<>();
//        List<TitleResponse> titleResponses = topicService.getTopicIdByTitleAndDate(createdOn, title);
//        dataResponse.setData(titleResponses);
//        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
//        return dataResponse;
//    }

    @RequestMapping(name = "getAllTopicByPagination", value = "/get/all/pagination",method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public PageResponse<TopicResponse> getAllTopicByPagination(@RequestBody FilterSortRequest<TopicFilter, TopicSortBy> filterSortRequest){
        PageResponse<TopicResponse> pageResponse= new PageResponse<>();
        Page<TopicResponse> page= topicService.getAllTopicByPagination(filterSortRequest.getFilter(),filterSortRequest.getSort()
                ,generalHelper.getPagination(filterSortRequest.getPagination().getPage(),filterSortRequest.getPagination().getLimit()));
        pageResponse.setData(page);
        pageResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return pageResponse;
    }


//    @RequestMapping(name = "getTopicIdByTitleAndDate", value = "/topicId/title/date", method = RequestMethod.GET)
//    @Access(levels = Role.ANONYMOUS)
//    public DataResponse<String> getTopicIdByTitleAndDate(@RequestParam String  createdOn, @RequestParam String title) throws JSONException {
//        DataResponse<String> dataResponse = new DataResponse<>();
//        topicService.getTopicIdByTitleAndCreatedOn(createdOn, title);
//        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
//        return dataResponse;
//    }

}
