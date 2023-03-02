package com.example.auth.stockPile.repository;

import com.example.auth.commons.decorator.CustomAggregationOperation;
import com.example.auth.decorator.pagination.CountQueryResult;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.stockPile.decorator.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.json.JSONException;
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
import org.springframework.data.support.PageableExecutionUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class TopicCustomRepositoryImpl implements TopicCustomRepository{

    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public List<TitleResponse> getTopicIdByTitleAndDate(String createdOn, String title) throws JSONException {
        List<AggregationOperation> operations=  getIdByTopic(createdOn,title);
        Aggregation aggregation=Aggregation.newAggregation(operations);
        return mongoTemplate.aggregate(aggregation,"topics",TitleResponse.class).getMappedResults();

    }

    private List<AggregationOperation> getIdByTopic(String createdOn, String title) throws JSONException {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        criteria = criteria.and("softDelete").is(false);
        operations.add(new CustomAggregationOperation(new Document("$group",
                new Document("_id", new Document("title", title).append("createdOn", createdOn))
                        .append("idInfo", new Document("$push", new Document("_id", "$_id")))
        )));
//        operations.add(new CustomAggregationOperation(new Document("$project", new Document("_id", 0))));
        operations.add(new CustomAggregationOperation(new Document("$limit", 1)));
        return operations;
    }



    @Override
    public Page<TopicResponse> getAllTopicByPagination(TopicFilter filter, FilterSortRequest.SortRequest<TopicSortBy> sort, PageRequest pagination) {

            List<AggregationOperation> operations = topicFilterAggregation(filter, sort, pagination, true);

            //Created Aggregation operation
            Aggregation aggregation = newAggregation(operations);

            List<TopicResponse> topics = mongoTemplate.aggregate(aggregation, "topics", TopicResponse.class).getMappedResults();

            // Find Count
            List<AggregationOperation> operationForCount = topicFilterAggregation(filter, sort, pagination, false);

            operationForCount.add(group().count().as("count"));
            operationForCount.add(project("count"));
            Aggregation aggregationCount = newAggregation(StockResponse.class, operationForCount);
            AggregationResults<CountQueryResult> countQueryResults = mongoTemplate.aggregate(aggregationCount, "topics", CountQueryResult.class);
            long count = countQueryResults.getMappedResults().size() == 0 ? 0 : countQueryResults.getMappedResults().get(0).getCount();
            return PageableExecutionUtils.getPage(
                    topics,
                    pagination,
                    () -> count);

        }


        Criteria getCriteria(TopicFilter topicFilter, List<AggregationOperation> operations) {
            Criteria criteria = new Criteria();
            operations.add(new CustomAggregationOperation(new Document("$addFields", new Document("search",
                    new Document("$concat", Arrays.asList(new Document("$ifNull", Arrays.asList("$title", ""))



                    ))))));
            if (!StringUtils.isEmpty(topicFilter.getSearch())) {
                topicFilter.setSearch(topicFilter.getSearch().replaceAll("\\|@\\|", ""));
                topicFilter.setSearch(topicFilter.getSearch().replaceAll("\\|@@\\|", ""));
                criteria = criteria.orOperator(
                        Criteria.where("search").regex(".*" + topicFilter.getSearch() + ".*", "i")
                );
            }
            if (!CollectionUtils.isEmpty(topicFilter.getIds())) {
                criteria = criteria.and("ids").is(topicFilter.getIds());
            }
            criteria = criteria.and("softDelete").is(false);
            return criteria;
        }

        private List<AggregationOperation> topicFilterAggregation(TopicFilter filter, FilterSortRequest.SortRequest<TopicSortBy> sort, PageRequest pagination, boolean addPage) {
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

}
