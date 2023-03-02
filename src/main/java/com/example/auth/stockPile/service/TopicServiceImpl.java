package com.example.auth.stockPile.service;


import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.stockPile.decorator.*;
import com.example.auth.stockPile.model.Stock;
import com.example.auth.stockPile.model.Topic;
import com.example.auth.stockPile.model.UserData;
import com.example.auth.stockPile.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;
    private final StockServiceImpl stockService;
    private final UserDataServiceImpl userDataService;
    private final UserHelper userHelper;

    public TopicServiceImpl(TopicRepository topicRepository, ModelMapper modelMapper, StockServiceImpl stockService, UserDataServiceImpl userDataService, UserHelper userHelper) {
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
        this.stockService = stockService;
        this.userDataService = userDataService;
        this.userHelper = userHelper;
    }

    @Override
    public TopicResponse addTopic(String stockId, String userId, TopicAddRequest topicAddRequest) {
        Stock stock = stockService.stockById(stockId);
        log.info("stockData:{}",stock);
        UserData userData = userDataService.userById(userId);

        Topic topic = modelMapper.map(topicAddRequest, Topic.class);
        topic.setStockId(stock.getId());
        topic.setStockSymbol(stock.getSymbol());
        topic.setStockName(stock.getName());
        topic.setCreatedOn(new Date());
        topic.setCreatedBy(userData);
        TopicResponse topicResponse = modelMapper.map(topic, TopicResponse.class);
        topicResponse.setCreatedBy(userData);
        topicRepository.save(topic);
        return topicResponse;

    }

    @Override
    public void updateTopic(String id, TopicAddRequest topicAddRequest) throws NoSuchFieldException, IllegalAccessException {
        Topic topic = topicById(id);
        update(id, topicAddRequest);
        userHelper.difference(topic, topicAddRequest);


    }

    @Override
    public List<TitleResponse> getTopicIdByTitleAndDate(String createdOn, String title) throws JSONException {
        log.info("this is inside serviceImp:{}");
        return topicRepository.getTopicIdByTitleAndDate(createdOn,title);
//           Topic topic= topicRepository.getIdByTitleAndCreatedOnAndSoftDeleteFalse(createdOn,topicTitle);
//        log.info("topicinfo:{}",topic);
//        if (topic!=null){
//            return topic.getId();
//        }
//        else {
//            throw new InvalidRequestException(MessageConstant.TITLE_NOT_FOUND);
//        }
    }

    @Override
    public Page<TopicResponse> getAllTopicByPagination(TopicFilter filter, FilterSortRequest.SortRequest<TopicSortBy> sort, PageRequest pagination) {
        return topicRepository.getAllTopicByPagination(filter,sort,pagination);
    }


    @Override
    public TopicResponse getTopicById(String id) {
        Topic topic = topicById(id);
        TopicResponse topicResponse = modelMapper.map(topic, TopicResponse.class);
        return topicResponse;
    }

    @Override
    public List<TopicResponse> getAllTopic() {
        List<Topic> topics = topicRepository.findAllBySoftDeleteFalse();
        List<TopicResponse> list = new ArrayList<>();

        if (!CollectionUtils.isEmpty(topics)){
            topics.sort(Comparator.comparing(Topic::getCreatedOn).reversed());
        }
        topics.forEach(topic -> {
            TopicResponse topicResponse = modelMapper.map(topic, TopicResponse.class);
            list.add(topicResponse);

        });

        return list;

    }

    @Override
    public void deleteTopicById(String id) {
        Topic topic = topicById(id);
        topic.setSoftDelete(true);
        topicRepository.save(topic);

    }

    private void update(String id, TopicAddRequest topicAddRequest) {
        Topic topic = topicById(id);
        if (topicAddRequest.getDescription() != null) {
            topic.setDescription(topicAddRequest.getDescription());

        }
        if (topicAddRequest.getTitle() != null) {
            topic.setTitle(topicAddRequest.getTitle());
        }

    }


    public Topic topicById(String id) {
        return topicRepository.getTopicByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }

//
//      public Topic topicIdByTitleAndCreatedOn(String createdOn,String topicTitle) {
//        return topicRepository.getTopicIdByTitleAndCreatedOnAndSoftDeleteIsFalse(createdOn,topicTitle).orElseThrow(() -> new NotFoundException(MessageConstant.TITLE_NOT_FOUND));
//    }
//
//


}
