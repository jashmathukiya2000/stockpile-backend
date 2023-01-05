package com.example.auth.controller;

import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.GeneralHelper;
import com.example.auth.decorator.*;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PageResponse;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import com.example.auth.model.PurchaseLogHistory;
import com.example.auth.service.PurchaseLogHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("purchaseLogHistory")

public class PurchaseLogHistoryController {
    private final PurchaseLogHistoryService purchaseLogHistoryService;
    private final GeneralHelper generalHelper;

    public PurchaseLogHistoryController(PurchaseLogHistoryService purchaseLogHistoryService, GeneralHelper generalHelper) {
        this.purchaseLogHistoryService = purchaseLogHistoryService;
        this.generalHelper = generalHelper;
    }

    @RequestMapping(name = "addPurchaseLog", value = "/add", method = RequestMethod.POST)
    public DataResponse<PurchaseLogHistoryResponse> addPurchaseLog(@RequestBody PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, @RequestParam String customerId) {
        DataResponse<PurchaseLogHistoryResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(purchaseLogHistoryService.addPurchaseLog(purchaseLogHistoryAddRequest, customerId));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "updatePurchaseLog", value = "/update/{id}", method = RequestMethod.POST)
    public DataResponse<Object> updatePurchaseLog(@RequestBody PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, @RequestParam String id) throws InvocationTargetException, IllegalAccessException {
        DataResponse<Object> dataResponse = new DataResponse<>();
        dataResponse.setData(purchaseLogHistoryService.updatePurchaseLog(purchaseLogHistoryAddRequest, id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.UPDATED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "getPurchaseLogById", value = "/get/{id}", method = RequestMethod.GET)
    public DataResponse<PurchaseLogHistoryResponse> getPurchaseLogById(@RequestParam String id) {
        DataResponse<PurchaseLogHistoryResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(purchaseLogHistoryService.getPurchaseLogById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

    @RequestMapping(name = "getAllPurchaseLog", value = "/get/All", method = RequestMethod.GET)
    public ListResponse<PurchaseLogHistoryResponse> getAllPurchaseLog() {
        ListResponse<PurchaseLogHistoryResponse> listResponse = new ListResponse<>();
        listResponse.setData(purchaseLogHistoryService.getAllPurchaseLog());
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }

    @RequestMapping(name = "deletePurchaseLogById", value = "/delete/{id}", method = RequestMethod.DELETE)
    public DataResponse<Object> deletePurchaseLogById(@RequestParam String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        dataResponse.setData(purchaseLogHistoryService.deletePurchaseLogById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));

        return dataResponse;
    }

    @RequestMapping(name = "getAllPurchaseLogByPagination", value = "get/all/pagination", method = RequestMethod.POST)
    public PageResponse<PurchaseLogHistoryResponse> getAllPurchaseLogByPagination(@RequestBody FilterSortRequest<PurchaseLogFilter, PurchaseLogSortBy> filterSortRequest) {
        PageResponse<PurchaseLogHistoryResponse> pageResponse = new PageResponse<>();
        Page<PurchaseLogHistoryResponse> logHistoryResponse = purchaseLogHistoryService.getAllPurchaseLogByPagination(filterSortRequest.getFilter(), filterSortRequest.getSort(),
                generalHelper.getPagination(filterSortRequest.getPagination().getPage(), filterSortRequest.getPagination().getLimit()));
        pageResponse.setData(logHistoryResponse);
        pageResponse.setStatus(Response.getOkResponse());
        return pageResponse;
    }


}
