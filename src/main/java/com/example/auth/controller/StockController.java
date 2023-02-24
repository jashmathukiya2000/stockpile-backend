package com.example.auth.controller;


import com.amazonaws.services.dynamodbv2.xspec.L;
import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.DataResponse;
import com.example.auth.commons.decorator.ListResponse;
import com.example.auth.commons.decorator.Response;
import com.example.auth.commons.enums.Role;
import com.example.auth.stockPile.decorator.StockAddRequest;
import com.example.auth.stockPile.decorator.StockResponse;
import com.example.auth.stockPile.service.StockService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stock_info")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @RequestMapping(name = "addStock",value = "/add", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<StockResponse> addStock(@RequestBody StockAddRequest stockAddRequest){
        DataResponse<StockResponse> dataResponse= new DataResponse<>();
        dataResponse.setData(stockService.addStock(stockAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "updateStock",value = "/update/stock", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Object> updateStock( @PathVariable String id,@RequestBody StockAddRequest stockAddRequest) throws NoSuchFieldException, IllegalAccessException {
        DataResponse<Object> dataResponse= new DataResponse<>();
        stockService.updateStock(id,stockAddRequest);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.UPDATED_SUCCESSFULLY));
        return dataResponse;
    }
  @RequestMapping(name = "getStockById",value = "/{id}", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<StockResponse> getStockById( @PathVariable String id){
        DataResponse<StockResponse> dataResponse= new DataResponse<>();
        dataResponse.setData(stockService.getStockById(id));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;
    }

    @RequestMapping(name = "getAllStock",value = "/get/all/stock", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public ListResponse<StockResponse> getAllStock(){
        ListResponse<StockResponse> listResponse= new ListResponse<>();
        listResponse.setData(stockService.getAllStock());
        listResponse.setStatus(Response.getOkResponse());
        return listResponse;
    }





  @RequestMapping(name = "deleteStockById",value = "/{id}", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Object> deleteStockById( @PathVariable String id){
        DataResponse<Object> dataResponse= new DataResponse<>();
        stockService.deleteStockById(id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));
        return dataResponse;
    }






}
