package com.example.auth.repository;

import com.example.auth.decorator.*;
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
        criteria = criteria.and("softDelete").is(false);
        criteria = criteria.and("age").is(userFilter.getAge());
        if (!StringUtils.isEmpty(userFilter.getOccupation()))
            criteria = criteria.and("occupation").is(userFilter.getOccupation());
        if (userFilter.getSalary() != 0)
            criteria = criteria.and("salary").is(userFilter.getSalary());
        query.addCriteria(criteria);
        return mongoTemplate.find(query, UserResponse.class, "auth");
    }

    public List<AggregationOperation> getUserBySalaryAggregation(UserFilter userFilter) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        criteria = criteria.and("softDelete").is(false);
        criteria = criteria.and("occupation").is(userFilter.getOccupation());
        operations.add(match(criteria));

        operations.add(new CustomAggregationOperation(new Document("$group",
                new Document("_id", "$salary")
                        .append("auth", new Document("$push", new Document("name", "$name")
                                .append("occupation", "$occupation")
                                .append("age", "$age")))
                        .append("count", new Document("$sum", 1))
                        .append("name", new Document("$first", "$name"))
                        .append("occupation", new Document("$last", "$occupation")))));
        operations.add(new CustomAggregationOperation(new Document("$sort", new Document("_id", 1))));
        return operations;
    }

    @Override
    public List<UserAggregationResponse> getUserByAggregation(UserFilter userFilter) {
        List<AggregationOperation> operations = getUserBySalaryAggregation(userFilter);
        Aggregation aggregation = newAggregation(operations);
        List<UserAggregationResponse> userAggregationResponses = mongoTemplate.aggregate(aggregation, "auth", UserAggregationResponse.class).getMappedResults();
        return userAggregationResponses;
    }

    public List<AggregationOperation> getResultBySpi(double spi) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        operations.add(new CustomAggregationOperation(new Document("$unwind", new Document("path", "$result"))));
        operations.add(new CustomAggregationOperation(new Document("$match", new Document("result.spi", spi).append("softDelete", false))));
        operations.add(new CustomAggregationOperation(new Document("$group", new Document("_id", "$result.spi")
                .append("auth", new Document("$push", new Document("name", "$name").append("email", "$email")
                        .append("_id", "$_id").append("semester", "$result.semester")))
                .append("sum", new Document("$sum", "$result.semester"))
                .append("count", new Document("$sum", 1))
                .append("average", new Document("$avg", "$result.semester")))));

        return operations;
    }

    @Override
    public List<UserSpiResponse> getUserBySpi(double spi) {
        List<AggregationOperation> operations = getResultBySpi(spi);
        Aggregation aggregation = newAggregation(operations);
        List<UserSpiResponse> userSpiResponse = mongoTemplate.aggregate(aggregation, "auth", UserSpiResponse.class).getMappedResults();
        return userSpiResponse;
    }

    public List<AggregationOperation> getUserByOccupation(String occupation) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        operations.add(new CustomAggregationOperation(new Document("$unwind",new Document("path","$result"))));
       operations.add(new CustomAggregationOperation(new Document("$match", new Document("occupation",occupation).append("softDelete",false))));
        operations.add(new CustomAggregationOperation(new Document("$group", new Document("_id", "$salary")
                .append("auth", new Document("$push", new Document("name", "$name")
                        .append("occupation", "$occupation")
                        .append("age", "$age").append("email", "$email")
                        .append("_id","$_id")
                        .append("semester", "$result.semester").append("spi", "$result.spi")))
                .append("count", new Document("$sum", 1))
                .append("name", new Document("$first", "$name"))
                .append("occuption", new Document("$last", "$occupation")))));
      operations.add(new CustomAggregationOperation(new Document("$sort", new Document("_id", 1))));
      operations.add(new CustomAggregationOperation(new Document("$limit", 1)));
        return operations;
    }

    @Override
    public List<OccupationResponse> getByOccupation(String occupation) {
        List<AggregationOperation> operations= getUserByOccupation(occupation);
            Aggregation aggregation= newAggregation(operations);
            List<OccupationResponse> occupationResponses=mongoTemplate.aggregate(aggregation,"auth",OccupationResponse.class)    .getMappedResults();
        return occupationResponses;
    }


}

