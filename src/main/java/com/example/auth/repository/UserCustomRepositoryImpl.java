package com.example.auth.repository;

import com.example.auth.decorator.CustomAggregationOperation;
import com.example.auth.decorator.pagination.CountQueryResult;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.UserFilterData;
import com.example.auth.decorator.pagination.UserSortBy;
import com.example.auth.decorator.user.*;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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
                new Document("id", "$salary")
                        .append("auth", new Document("$push", new Document("name", "$categoryName")
                                .append("occupation", "$occupation")
                                .append("age", "$age")))
                        .append("count", new Document("$sum", 1))
                        .append("name", new Document("$first", "$categoryName"))
                        .append("occupation", new Document("$last", "$occupation")))));
        operations.add(new CustomAggregationOperation(new Document("$sort", new Document("id", 1))));
        return operations;
    }

    @Override
    public List<UserAggregationResponse> getUserByAggregation(UserFilter userFilter) {
        List<AggregationOperation> operations = getUserBySalaryAggregation(userFilter);
        Aggregation aggregation = newAggregation(operations);
        return mongoTemplate.aggregate(aggregation, "auth", UserAggregationResponse.class).getMappedResults();

    }

    public List<AggregationOperation> getResultBySpi(double spi) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(new CustomAggregationOperation(new Document("$unwind", new Document("path", "$result"))));
        operations.add(new CustomAggregationOperation(new Document("$match", new Document("result.spi", spi).append("softDelete", false))));
        operations.add(new CustomAggregationOperation(new Document("$group", new Document("id", "$result.spi")
                .append("auth", new Document("$push", new Document("name", "$categoryName").append("email", "$email")
                        .append("id", "$id").append("semester", "$result.semester")))
                .append("sum", new Document("$sum", "$result.semester"))
                .append("count", new Document("$sum", 1))
                .append("average", new Document("$avg", "$result.semester")))));

        return operations;
    }

    @Override
    public List<UserSpiResponse> getUserBySpi(double spi) {
        List<AggregationOperation> operations = getResultBySpi(spi);
        Aggregation aggregation = newAggregation(operations);
        return mongoTemplate.aggregate(aggregation, "auth", UserSpiResponse.class).getMappedResults();

    }

//    public List<AggregationOperation> getUserByOccupation(String occupation) {
//        List<AggregationOperation> operations = new ArrayList<>();
//        operations.add(new CustomAggregationOperation(new Document("$unwind", new Document("path", "$result"))));
//        operations.add(new CustomAggregationOperation(new Document("$match", new Document("occupation", occupation).append("softDelete", false))));
//        operations.add(new CustomAggregationOperation(new Document("$group", new Document("id", "$salary")
//                .append("auth", new Document("$push", new Document("categoryName", "$categoryName")
//                        .append("occupation", "$occupation")
//                        .append("age", "$age").append("email", "$email")
//                        .append("id", "$id")
//                        .append("semester", "$result.semester").append("spi", "$result.spi")))
//                .append("count", new Document("$sum", 1))
//                .append("categoryName", new Document("$first", "$categoryName"))
//                .append("occuption", new Document("$last", "$occupation").append("maxSpi",
//                        new Document("$max","$result").append("minSpi",
//                                new Document("$min","$result")))))));
//        operations.add(new CustomAggregationOperation(new Document("$sort", new Document("id", 1))));
//        operations.add(new CustomAggregationOperation(new Document("$limit", 1)));
//        return operations;
//    }


//    @Override
//    public List<OccupationResponse> getByOccupation(String occupation) {
//        List<AggregationOperation> operations = getUserByOccupation(occupation);
//        Aggregation aggregation = newAggregation(operations);
//        return mongoTemplate.aggregate(aggregation, "auth", OccupationResponse.class).getMappedResults();
//
//    }

    @Override
    public Page<UserResponse> getAllUserByPagination(UserFilterData filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pagination) {
        List<AggregationOperation> operations = userFilterAggregation(filter, sort, pagination, true);
        //Created Aggregation operation
        Aggregation aggregation = newAggregation(operations);

        List<UserResponse> users = mongoTemplate.aggregate(aggregation, "auth", UserResponse.class).getMappedResults();


        // Find Count
        List<AggregationOperation> operationForCount = userFilterAggregation(filter, sort, pagination, false);
        operationForCount.add(group().count().as("count"));
        operationForCount.add(project("count"));
        System.out.println("hello" + users);
        Aggregation aggregationCount = newAggregation(UserResponse.class, operationForCount);
        AggregationResults<CountQueryResult> countQueryResults = mongoTemplate.aggregate(aggregationCount, "auth", CountQueryResult.class);
        long count = countQueryResults.getMappedResults().size() == 0 ? 0 : countQueryResults.getMappedResults().get(0).getCount();
        return PageableExecutionUtils.getPage(
                users,
                pagination,
                () -> count);

    }


    Criteria getCriteria(UserFilterData userFilter, List<AggregationOperation> operations) {
        Criteria criteria = new Criteria();
        operations.add(new CustomAggregationOperation(new Document("$addFields", new Document("search",
                new Document("$concat", Arrays.asList(new Document("$ifNull", Arrays.asList("$firstName", "")),
                        "|@|", new Document("$ifNull", Arrays.asList("$lastName", "")),
                        "|@|", new Document("$ifNull", Arrays.asList("$address.zip", "")),
                        "|@|", new Document("$ifNull", Arrays.asList("$occupation", ""))


                ))))));

        if (!StringUtils.isEmpty(userFilter.getSearch())) {
            userFilter.setSearch(userFilter.getSearch().replaceAll("\\|@\\|", ""));
            userFilter.setSearch(userFilter.getSearch().replaceAll("\\|@@\\|", ""));
            criteria = criteria.orOperator(
                    Criteria.where("search").regex(".*" + userFilter.getSearch() + ".*", "i")
            );
        }
        criteria = criteria.and("_id").in(userFilter.getId());
        criteria = criteria.and("softDelete").is(false);
        return criteria;
    }

    private List<AggregationOperation> userFilterAggregation(UserFilterData filter, FilterSortRequest.SortRequest<UserSortBy> sort, PageRequest pagination, boolean addPage) {
        List<AggregationOperation> operations = new ArrayList<>();

        operations.add(match(getCriteria(filter, operations)));
        if (addPage) {
            //sorting
            if (sort != null && sort.getSortBy() != null && sort.getOrderBy() != null) {
                operations.add(new SortOperation(Sort.by(sort.getOrderBy(), sort.getSortBy().getValue())));
            }
            if (pagination != null) {
                operations.add(skip(pagination.getOffset()));
                operations.add(limit(pagination.getPageSize()));

            }
        }
        return operations;
    }


    @Override
    public List<MaxSpiResponse> getUserByMaxSpi(String id) {
        List<AggregationOperation> operations = getByMaxSpi(id);
        Aggregation aggregation = newAggregation(operations);
        return mongoTemplate.aggregate(aggregation, "auth", MaxSpiResponse.class).getMappedResults();
    }

    public List<AggregationOperation> getByMaxSpi(String id) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(new CustomAggregationOperation(new Document("$match", new Document("id", id).append("softDelete", false))));
        operations.add(new CustomAggregationOperation(new Document("$group",
                new Document("id", "$id")
                        .append("auth", new Document("$push",
                                new Document("name", "$categoryName")
                                        .append("maxSpi", new Document("$max", "$result"))
                                        .append("minSpi", new Document("$min", "$result")))))));
        return operations;
    }


}




