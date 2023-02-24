package com.example.auth.stockPile.service;


import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.stockPile.decorator.TopicAddRequest;
import com.example.auth.stockPile.decorator.TopicResponse;
import com.example.auth.stockPile.model.Stock;
import com.example.auth.stockPile.model.Topic;
import com.example.auth.stockPile.model.UserData;
import com.example.auth.stockPile.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService{
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

        UserData userData= userDataService.userById(userId);

        Topic topic = modelMapper.map(topicAddRequest, Topic.class);

        topic.setStocks(stock.getId());

        topic.setCreatedOn(new Date());

        topic.setCreatedBy(userData.getId());

        TopicResponse topicResponse = modelMapper.map(topic, TopicResponse.class);

        topicResponse.setCreatedBy(userData);

        topicRepository.save(topic);

        return topicResponse;

    }

    @Override
    public void updateTopic(String id, TopicAddRequest topicAddRequest) throws NoSuchFieldException, IllegalAccessException {
        Topic topic=topicById(id);
        update(id,topicAddRequest);
        userHelper.difference(topic,topicAddRequest);



    }


    @Override
    public TopicResponse getTopicById(String id) {
        Topic topic=topicById(id);
        TopicResponse topicResponse= modelMapper.map(topic,TopicResponse.class);
        return topicResponse;
    }

    @Override
    public List<TopicResponse> getAllTopic() {
        List<Topic> topics= topicRepository.findAllBySoftDeleteFalse();
        List<TopicResponse> list=new ArrayList<>();
        topics.forEach(topic -> {
            TopicResponse topicResponse= modelMapper.map(topic,TopicResponse.class);
            list.add(topicResponse);
        });

        return list;

    }

    @Override
    public void deleteTopicById(String id) {
        log.info("deleteToicById:{}");
        Topic topic=topicById(id);
        topic.setSoftDelete(true);
        topicRepository.save(topic);

    }

    private void update(String id, TopicAddRequest topicAddRequest) {
        Topic topic=topicById(id);
       if (topicAddRequest.getDescription()!=null){
           topic.setDescription(topicAddRequest.getDescription());

       }
       if (topicAddRequest.getTitle()!=null){
           topic.setTitle(topicAddRequest.getTitle());
       }

    }



    public Topic topicById(String id){
        return topicRepository.getTopicByIdAndSoftDeleteIsFalse(id).orElseThrow(()-> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }

}
