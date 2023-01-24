package com.example.auth.service;

import com.example.auth.decorator.PurchaseLogHistoryAddRequest;
import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import com.example.auth.model.PurchaseLogHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface PurchaseLogHistoryService {
    PurchaseLogHistoryResponse addPurchaseLog(PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, String customerId);

    Object updatePurchaseLog(PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, String id) throws InvocationTargetException, IllegalAccessException;

    PurchaseLogHistoryResponse getPurchaseLogById(String id);

    List<PurchaseLogHistoryResponse> getAllPurchaseLog();


    Object deletePurchaseLogById(String id);

    Page<PurchaseLogHistoryResponse> getAllPurchaseLogByPagination(PurchaseLogFilter purchaseLogFilter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pageRequest);


    List<PurchaseLogHistory> findById(String customerId);


    void save(MultipartFile file);


    List<PurchaseLogHistoryResponse> getPurchaseLogByMonth(int month);

}
