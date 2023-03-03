package com.example.auth.stockPile.service;

import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.stockPile.decorator.StockAddRequest;

import com.example.auth.stockPile.decorator.StockFilter;
import com.example.auth.stockPile.decorator.StockResponse;
import com.example.auth.stockPile.decorator.StockSortBy;
import com.example.auth.stockPile.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

public interface StockService {

    StockResponse addStock(StockAddRequest stockAddRequest);


    StockResponse getStockById(String id);

    void updateStock(String id, StockAddRequest stockAddRequest) throws NoSuchFieldException, IllegalAccessException;

    void deleteStockById(String id);

    List<StockResponse> getAllStock();


    Page<StockResponse> getAllStockByPagination(StockFilter filter, FilterSortRequest.SortRequest<StockSortBy> sort, PageRequest pagination);

    StockResponse getStockSubscription(String symbol, String userId);

//    Map<String, List<Stock>> allSubscribers();


}
