package com.example.auth.repository;

import com.example.auth.decorator.CustomAggregationOperation;
import com.example.auth.decorator.ItemResponse;
import com.example.auth.decorator.pagination.CountQueryResult;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.ItemFilter;
import com.example.auth.decorator.pagination.ItemSortBy;
import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class ItemCustomRepositoryImpl implements ItemCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Page<ItemResponse> getAllItemsByPagination(ItemFilter filter, FilterSortRequest.SortRequest<ItemSortBy> sort, PageRequest pageRequest) {
        List<AggregationOperation> operations = itemAggregationFilter(filter, sort, pageRequest, true);
        Aggregation aggregation = newAggregation(operations);
        List<ItemResponse> category = mongoTemplate.aggregate(aggregation, "items", ItemResponse.class).getMappedResults();
        //FindCount
        List<AggregationOperation> operationsForCount = itemAggregationFilter(filter, sort, pageRequest, false);
        operationsForCount.add(group().count().as("count"));
        operationsForCount.add(project("count"));
        Aggregation aggregationForCount = newAggregation(ItemResponse.class, operationsForCount);
        AggregationResults<CountQueryResult> countQueryResults = mongoTemplate.aggregate(aggregationForCount, "items", CountQueryResult.class);
        long count = countQueryResults.getMappedResults().size() == 0 ? 0 : countQueryResults.getMappedResults().get(0).getCount();
        return PageableExecutionUtils.getPage(
                category,
                pageRequest,
                () -> count);
    }

    private List<AggregationOperation> itemAggregationFilter(ItemFilter filter, FilterSortRequest.SortRequest<ItemSortBy> sort, PageRequest pageRequest, boolean addPage) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(match(getCriteria(filter, operations)));
        if (addPage) {
            //sorting
            if (sort != null && sort.getSortBy() != null && sort.getOrderBy() != null) {
                operations.add(new SortOperation(Sort.by(sort.getOrderBy(), sort.getSortBy().getValue())));
            }
            if (pageRequest != null) {
                operations.add(skip(pageRequest.getOffset()));
                operations.add(limit(pageRequest.getPageSize()));
            }
        }
        return operations;
    }

    private Criteria getCriteria(ItemFilter filter, List<AggregationOperation> operations) {
        Criteria criteria = new Criteria();
        operations.add(new CustomAggregationOperation(
                new Document("$addFields",
                        new Document("search",
                                new Document("$concat", Arrays.asList(
                                        new Document("$ifNull", Arrays.asList("$itemName", ""))
                                )
                                )
                        ))
        ));
        if (!StringUtils.isEmpty(filter.getSearch())) {
            filter.setSearch(filter.getSearch().replaceAll("\\|@\\|", ""));
            filter.setSearch(filter.getSearch().replaceAll("\\|@@\\|", ""));
            criteria = criteria.orOperator(
                    Criteria.where("search").regex(".*" + filter.getSearch() + ".*", "i")
            );
        }

        if (!CollectionUtils.isEmpty(filter.getId())) {
            criteria = criteria.and("_id").is(filter.getId());
        }

        criteria = criteria.and("softDelete").is(false);
        return criteria;
    }
}

