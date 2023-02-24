package com.example.auth.stockPile.service;

import com.example.auth.stockPile.decorator.StockAddRequest;

import com.example.auth.stockPile.decorator.StockResponse;

import java.util.List;

public interface StockService {

    StockResponse addStock(StockAddRequest stockAddRequest);


    StockResponse getStockById(String id);

    void updateStock(String id, StockAddRequest stockAddRequest) throws NoSuchFieldException, IllegalAccessException;

    void deleteStockById(String id);

    List<StockResponse> getAllStock();


}
