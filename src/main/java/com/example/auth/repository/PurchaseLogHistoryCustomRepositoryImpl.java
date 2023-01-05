package com.example.auth.repository;

import com.example.auth.decorator.CustomAggregationOperation;
import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.decorator.pagination.CountQueryResult;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import com.example.auth.model.PurchaseLogHistory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Slf4j
public class PurchaseLogHistoryCustomRepositoryImpl implements PurchaseLogHistoryCustomRepository {

    private final MongoTemplate mongoTemplate;

    public PurchaseLogHistoryCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<PurchaseLogHistoryResponse> getAllPurchaseLogByPagination(PurchaseLogFilter purchaseLogFilter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pageRequest) {
        List<AggregationOperation> operations = filterAggregation(purchaseLogFilter, sort, pageRequest, true);

        //Created Aggregation operation
        Aggregation aggregation = newAggregation(operations);

        List<PurchaseLogHistoryResponse> purchaseLogHistoryResponse = mongoTemplate.aggregate(aggregation, "purchaseLogHistory", PurchaseLogHistoryResponse.class).getMappedResults();

        // Find Count
        List<AggregationOperation> operationForCount = filterAggregation(purchaseLogFilter, sort, pageRequest, false);
        operationForCount.add(group().count().as("count"));
        operationForCount.add(project("count"));
        Aggregation aggregationCount = newAggregation(PurchaseLogHistory.class, operationForCount);
        AggregationResults<CountQueryResult> countQueryResults = mongoTemplate.aggregate(aggregationCount, "purchaseLogHistory", CountQueryResult.class);
        long count = countQueryResults.getMappedResults().size() == 0 ? 0 : countQueryResults.getMappedResults().get(0).getCount();
        return PageableExecutionUtils.getPage(
                purchaseLogHistoryResponse,
                pageRequest,
                () -> count);
    }


    private List<AggregationOperation> filterAggregation(PurchaseLogFilter purchaseLogFilter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pageRequest, boolean addPage) {
        List<AggregationOperation> operationList = new ArrayList<>();
        operationList.add(match(getCriteria(purchaseLogFilter, operationList)));
        if (addPage) {
            if (sort != null && sort.getSortBy() != null && sort.getOrderBy() != null) {
                operationList.add(new SortOperation(Sort.by(sort.getOrderBy(), sort.getSortBy().getValue())));
            }
            if (pageRequest != null) {

                operationList.add(skip(pageRequest.getOffset()));
                operationList.add(limit(pageRequest.getPageSize()));
            }
        }
        return operationList;
    }

    private Criteria getCriteria(PurchaseLogFilter purchaseLogFilter, List<AggregationOperation> operationList) {
        Criteria criteria = searchCriteria(purchaseLogFilter.getSearch(), operationList);
        if (!CollectionUtils.isEmpty(purchaseLogFilter.getIds())) {
            criteria = criteria.and("_id").is(purchaseLogFilter.getIds());
        }
        criteria = criteria.and("softDelete").is(purchaseLogFilter.isSoftDelete());

        return criteria;
    }

    private Criteria searchCriteria(String search, List<AggregationOperation> operationList) {
        Criteria criteria = new Criteria();
        operationList.add(
                new CustomAggregationOperation(
                        new Document("$addFields",
                                new Document("search",
                                        new Document("$concat",
                                                Arrays.asList(new Document("$ifNull", Arrays.asList("$itemName", ""))
                                                )
                                        )
                                )
                        )

                )
        );
        if (!StringUtils.isEmpty(search)) {
            search = search.replace("\\|@\\|", "");
//        search=search.replace("\\|@@\\|","");

            criteria = criteria.orOperator(Criteria.where("search").regex(".*" + search + ".*", "i")
            );

        }
        return criteria;


    }


}
