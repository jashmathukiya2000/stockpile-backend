package com.example.auth.controller;


import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.DataResponse;
import com.example.auth.commons.decorator.ListResponse;
import com.example.auth.commons.decorator.Response;
import com.example.auth.commons.enums.Role;
import com.example.auth.stockPile.decorator.TopicAddRequest;
import com.example.auth.stockPile.decorator.TopicResponse;
import com.example.auth.stockPile.service.TopicService;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
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

    @RequestMapping(name = "getTopicById", value = "/{id}", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<TopicResponse> addTopic(@RequestParam String id) {
        DataResponse<TopicResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(topicService.getTopicById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

    @RequestMapping(name = "getAllTopic", value = "/get/all/topic", method = RequestMethod.POST)
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


}
