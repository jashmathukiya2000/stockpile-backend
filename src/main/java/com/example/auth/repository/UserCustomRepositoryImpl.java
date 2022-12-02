package com.example.auth.repository;

import com.example.auth.decorator.UserFilter;
import com.example.auth.decorator.UserResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserCustomRepositoryImpl implements UserCustomRepository{
    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public List<UserResponse> findByAgeAndSoftDeleteFalse(UserFilter userFilter ) {
        Query query= new Query();
        Criteria criteria= new Criteria();
        criteria=criteria.and("SoftDelete").is(false);
        if (!StringUtils.isEmpty(userFilter.getAge()))
        criteria=criteria.and("age").is(userFilter.getAge());
        if (!StringUtils.isEmpty(userFilter.getOccupation()))
            criteria=criteria.and("occupation").is(userFilter.getOccupation());
        query.addCriteria(criteria);
        return mongoTemplate.find(query,UserResponse.class, "auth");

    }

//    @Override
//    public List<UserResponse> findOccupationAndSoftDeleteFalse(UserFilter userFilter) {
//        Query query= new Query();
//        Criteria criteria= new Criteria();
//        criteria=criteria.and("SoftDelete").is(false);
//        if (!StringUtils.isEmpty(userFilter.getOccupation()))
//            criteria=criteria.and("occupation").is(userFilter.getOccupation());
//        query.addCriteria(criteria);
//        return mongoTemplate.find(query,UserResponse.class);
//    }
}
