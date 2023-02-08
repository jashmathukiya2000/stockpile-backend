package com.example.auth.repository;

import com.example.auth.decorator.ItemPurchaseAggregationResponse;
import com.example.auth.decorator.MainDateFilter;
import com.example.auth.decorator.PurchaseAggregationResponse;
import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PurchaseLogHistoryCustomRepository {
    Page<PurchaseLogHistoryResponse> getAllPurchaseLogByPagination(PurchaseLogFilter purchaseLogFilter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pageRequest);


    public List<PurchaseAggregationResponse> findItemPurchaseDetailsByMonthYear() throws JSONException;

    public List<ItemPurchaseAggregationResponse> getPurchaseDetailsByCustomerName();


    Page<PurchaseLogHistoryResponse> getPurchaseLogByMonth(PurchaseLogFilter filter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pagination) throws JSONException;

    Page<ItemPurchaseAggregationResponse> getPurchaseDetailsByCustomer(PurchaseLogFilter filter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pageRequest) throws JSONException;

    Page<MainDateFilter> getDateFilters(PurchaseLogFilter filter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pagination, MainDateFilter mainDateFilter) throws JSONException;
}
