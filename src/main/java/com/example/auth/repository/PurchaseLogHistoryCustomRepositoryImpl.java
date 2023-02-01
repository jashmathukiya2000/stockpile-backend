package com.example.auth.repository;

import com.example.auth.commons.decorator.FileReader;
import com.example.auth.commons.model.AggregationUtils;
import com.example.auth.decorator.CustomAggregationOperation;
import com.example.auth.decorator.ItemPurchaseAggregationResponse;
import com.example.auth.decorator.PurchaseAggregationResponse;
import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.decorator.pagination.CountQueryResult;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import com.example.auth.model.PurchaseLogHistory;
import com.google.api.client.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
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

import java.util.*;

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

//    @Override
//    public List<PurchaseLogHistoryResponse> getPurchaseLogByMonth(int month) {
//        List<AggregationOperation> operations = getPurchaseLogByMonthAndYear(month);
//        Aggregation aggregation = newAggregation(operations);
//        log.info("output:{}" + aggregation);
//        return mongoTemplate.aggregate(aggregation, "purchaseLogHistory", PurchaseLogHistoryResponse.class).getMappedResults();
//
//    }

    public List<AggregationOperation> getPurchaseLogByMonthAndYear(int month) {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        criteria = criteria.and("softDelete").is(false);
        operations.add(match(criteria));
        operations.add(new CustomAggregationOperation(new Document("$addFields",
                new Document("month", new Document("$month", "$date"))
                        .append("year", new Document("$year", "$date")))));
        operations.add(new CustomAggregationOperation(new Document("$match", new Document("month", month))));

        log.info("output:{}" + operations);
        return operations;
    }


    @Override
    public List<PurchaseAggregationResponse> findItemPurchaseDetailsByMonthYear() {
        List<AggregationOperation> operations = getItemPurchaseByMonthAndYear();
        Aggregation aggregation = newAggregation(operations);
        log.info("output:{}", operations);
        return mongoTemplate.aggregate(aggregation, "purchaseLogHistory", PurchaseAggregationResponse.class).getMappedResults();
    }

    public List<AggregationOperation> getItemPurchaseByMonthAndYear() {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        criteria = criteria.and("softDelete").is(false);
        operations.add(match(criteria));
        operations.add(new CustomAggregationOperation(new Document("$set",
                new Document("month", new Document("$month", "$date"))
                        .append("year", new Document("$year", "$date")))));
        operations.add(new CustomAggregationOperation(new Document("$group", new Document("_id",
                new Document("month", "$month").append("year", "$year")
                        .append("itemName", "$itemName"))
                .append("itemDetails", new Document("$push", new Document("customerId", "$customerId")
                        .append("price", "$price")))
                .append("count", new Document("$sum", 1.0))
                .append("totalPrice", new Document("$sum", "$price")))));
        operations.add(new CustomAggregationOperation(new Document("$group",
                new Document("_id", new Document("month", "$_id.month").append("year", "$_id.year"))
                        .append("itemDetail", new Document("$push",
                                new Document("itemName", "$_id.itemName")
                                        .append("count", "$count")
                                        .append("totalPrice", new Document("$sum", "" + "$itemDetails.price"))))
                        .append("totalItem", new Document("$sum", "$count"))
        )));
        return operations;
    }

    @Override
    public List<ItemPurchaseAggregationResponse> getPurchaseDetailsByCustomerName() {
        List<AggregationOperation> operations = getDetailsByCustomerName();
        Aggregation aggregation = newAggregation(operations);
        return mongoTemplate.aggregate(aggregation, "purchaseLogHistory", ItemPurchaseAggregationResponse.class).getMappedResults();
    }

    @Override
    public Page<PurchaseLogHistoryResponse> getPurchaseLogByMonth(PurchaseLogFilter filter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pagination) throws JSONException {
        List<AggregationOperation> operations = getPurchaseDetailsByMonth(filter, sort, pagination, true);
        Aggregation aggregation = newAggregation(operations);
        List<PurchaseLogHistoryResponse> purchaseLogHistoryResponses = mongoTemplate.aggregate(aggregation, "purchaseLogHistory", PurchaseLogHistoryResponse.class).getMappedResults();
        List<AggregationOperation> operationList = getPurchaseDetailsByMonth(filter, sort, pagination, true);
        operationList.add(group().count().as("count"));
        operationList.add(project("count"));
        Aggregation aggregationCount = newAggregation(PurchaseLogHistoryResponse.class, operationList);
        AggregationResults<CountQueryResult> countQueryResults = mongoTemplate.aggregate(aggregationCount, "purchaseLogHistory", CountQueryResult.class);
        long count = countQueryResults.getMappedResults().size() == 0 ? 0 : countQueryResults.getMappedResults().get(0).getCount();
        return PageableExecutionUtils.getPage(
                purchaseLogHistoryResponses,
                pagination,
                () -> count);

    }

    @Override
    public Page<ItemPurchaseAggregationResponse> getPurchaseDetailsByCustomer(PurchaseLogFilter filter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pageRequest) throws JSONException {
        List<AggregationOperation> operations = purchaseDetailsByCustomer(filter, sort, pageRequest, true);
        Aggregation aggregation = newAggregation(operations);
        List<ItemPurchaseAggregationResponse> itemPurchaseAggregation = mongoTemplate.aggregate(aggregation, "purchaseLogHistory", ItemPurchaseAggregationResponse.class).getMappedResults();
        List<AggregationOperation> operationList = purchaseDetailsByCustomer(filter, sort, pageRequest, true);
        operationList.add(group().count().as("count"));
        operations.add(project("count"));
        Aggregation aggregation1 = newAggregation(PurchaseLogHistory.class, operationList);
        AggregationResults<CountQueryResult> countQueryResults = mongoTemplate.aggregate(aggregation1, "purchaseLogHistory", CountQueryResult.class);
        long count = countQueryResults.getMappedResults().size() == 0 ? 0 : countQueryResults.getMappedResults().get(0).getCount();
        return PageableExecutionUtils.getPage(
                itemPurchaseAggregation,
                pageRequest,
                () -> count);

    }

    private List<AggregationOperation> purchaseDetailsByCustomer(PurchaseLogFilter filter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pageRequest, boolean addPage) throws JSONException {
        List<AggregationOperation> operations = new ArrayList<>();
        String fileName = FileReader.loadFile("aggregation/PurchaseLogByMonthYear.json");
        JSONObject jsonObject = new JSONObject(fileName);
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "match", Object.class))));
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "mergeCustomerData", Object.class))));
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "unwindCustomer", Object.class))));
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "setCustomer", Object.class))));
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "unsetCustomer", Object.class))));
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "groupByItemName", Object.class))));
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "groupByCustomerName", Object.class))));
        if (addPage) {
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

    public List<AggregationOperation> getPurchaseDetailsByMonth(PurchaseLogFilter filter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pagination, boolean addPage) throws JSONException {
        List<AggregationOperation> operations = new ArrayList<>();
        String loadFile = FileReader.loadFile("aggregation/PurchaseLogByMonthInExcel.json");
        JSONObject jsonObject = new JSONObject(loadFile);
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "addDate", Object.class))));
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "matchMonth", Object.class))));
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "groupByMonth", Object.class))));
        operations.add(new CustomAggregationOperation(Document.parse(CustomAggregationOperation.getJson(jsonObject, "groupByCustomerId", Object.class))));
        if (addPage) {
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
    public List<AggregationOperation> getDetailsByCustomerName() {
        List<AggregationOperation> operations = new ArrayList<>();
        Criteria criteria = new Criteria();
        criteria = criteria.and("softDelete").is(false);
        operations.add(match(criteria));
        Map<String, Object> let = new HashMap<>();
        let.put("customerId", "$customerId");
        List<Document> pipeline = new ArrayList<>();
        pipeline.add(new Document("$match",
                new Document("$expr",
                        new Document("$and",
                                Collections.singletonList(new Document("$eq", Arrays.asList(new Document("$toString", "$_id"), "$$customerId")))))));
        operations.add(AggregationUtils.lookup("customer", let, pipeline, "customerDetail"));
        operations.add(new CustomAggregationOperation(new Document("$unwind", new Document("path", "$customerDetail"))));
        operations.add(new CustomAggregationOperation(new Document("$set", new Document("name", "$customerDetail.name")
                .append("contact", "$customerDetail.contact"))));
        operations.add(new CustomAggregationOperation(new Document("$unset", "customerDetail")));
        operations.add(new CustomAggregationOperation(new Document("$group",
                new Document("_id", new Document("customerName", "$customerName")
                        .append("itemName", "$itemName"))
                        .append("itemDetails",
                                new Document("$push", new Document("date",
                                        new Document(" $dateToString", new Document("format", "%m-%d-%Y")
                                                .append("date", "$date")
                                                .append("timezone", "America/Chicago")))
                                        .append("price", "$price")))
                        .append("count", new Document("$sum", 1))
                        .append("price", new Document("$sum", "$price")))));
        operations.add(new CustomAggregationOperation(new Document("$group",
                new Document("_id", "$_id.customerName")
                        .append("itemDetail",
                                new Document("$push", new Document("date", new Document("$last", "$itemDetails.date"))
                                        .append("itemName", "$_id.itemName")
                                        .append("count", new Document("$sum", "$count"))
                                        .append("totalPrice",
                                                new Document("$sum", "$itemDetails.price")))))));
        return operations;
    }
}
