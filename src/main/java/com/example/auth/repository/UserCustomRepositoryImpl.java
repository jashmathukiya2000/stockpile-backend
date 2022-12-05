package com.example.auth.repository;

import com.example.auth.decorator.CustomAggregationOperation;
import com.example.auth.decorator.UserAggregationResponse;
import com.example.auth.decorator.UserFilter;
import com.example.auth.decorator.UserResponse;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<UserResponse> getByFilterAndSoftDeleteFalse(UserFilter userFilter) {

        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria = criteria.and("SoftDelete").is(false);
        if (!StringUtils.isEmpty(userFilter.getAge()))
            criteria = criteria.and("age").is(userFilter.getAge());
        if (!StringUtils.isEmpty(userFilter.getOccupation()))
            criteria = criteria.and("occupation").is(userFilter.getOccupation());
        if (!StringUtils.isEmpty(userFilter.getSalary()))
            criteria = criteria.and("salary").is(userFilter.getSalary());
        query.addCriteria(criteria);
        return mongoTemplate.find(query, UserResponse.class, "auth");
    }

    @Override
    public List<UserAggregationResponse> getUserByAggregation(UserFilter userFilter){
        List<AggregationOperation> operations = getUserBySalaryAggregation(userFilter);
        Aggregation aggregation =  newAggregation(operations);
        return  mongoTemplate.aggregate(aggregation, "auth",UserAggregationResponse.class).getMappedResults();
    }


    public List<AggregationOperation> getUserBySalaryAggregation(UserFilter userFilter) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        if (!StringUtils.isEmpty(userFilter.getSalary()))
            criteria = criteria.and("SoftDelete").is(false);
        criteria = criteria.and("salary").is(userFilter.getSalary());
        operations.add(match(criteria));

        operations.add(new CustomAggregationOperation(new Document("$group",
                new Document("_id", "$salary")
                        .append("auth", new Document("$push", new Document("name", "$name")
                                .append("occupation", "$occupation"))))));
        return operations;
    }
}
