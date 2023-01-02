package com.example.auth.controller;

import com.example.auth.decorator.DataResponse;
import com.example.auth.decorator.PurchaseLogHistoryAddRequest;
import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.decorator.Response;
import com.example.auth.service.PurchaseLogHistoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("purchaseLogHistory")
public class PurchaseLogHistoryController {
    private final PurchaseLogHistoryService purchaseLogHistoryService;

    public PurchaseLogHistoryController(PurchaseLogHistoryService purchaseLogHistoryService) {
        this.purchaseLogHistoryService = purchaseLogHistoryService;
    }

    @RequestMapping(name = "addPurchaseLog", value = "/add",method = RequestMethod.POST)
    public DataResponse<PurchaseLogHistoryResponse> addPurchaseLog(@RequestBody PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest,@RequestParam String customerId){
        DataResponse<PurchaseLogHistoryResponse> purchaseLogHistory=new DataResponse<>();
        purchaseLogHistory.setData(purchaseLogHistoryService.addPurchaseLog(purchaseLogHistoryAddRequest,customerId));
        purchaseLogHistory.setStatus(Response.getOkResponse());
        return purchaseLogHistory;
    }

}
