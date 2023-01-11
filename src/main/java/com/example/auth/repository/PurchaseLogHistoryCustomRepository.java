package com.example.auth.repository;

import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PurchaseLogHistoryCustomRepository {
    Page<PurchaseLogHistoryResponse> getAllPurchaseLogByPagination(PurchaseLogFilter purchaseLogFilter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pageRequest);
    public List<PurchaseLogHistoryResponse> getPurchaseLogByMonth(int month);
}
