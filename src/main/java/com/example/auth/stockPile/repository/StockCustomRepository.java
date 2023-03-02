package com.example.auth.stockPile.repository;

import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.stockPile.decorator.StockFilter;
import com.example.auth.stockPile.decorator.StockResponse;
import com.example.auth.stockPile.decorator.StockSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface StockCustomRepository {
    Page<StockResponse> getAllStockByPagination(StockFilter filter, FilterSortRequest.SortRequest<StockSortBy> sort, PageRequest pagination);
}
