package com.example.auth.service;

import com.example.auth.decorator.PurchaseLogHistoryAddRequest;
import com.example.auth.decorator.PurchaseLogHistoryResponse;

public interface PurchaseLogHistoryService {
    PurchaseLogHistoryResponse addPurchaseLog(PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, String customerId);
}
