package com.example.auth.stockPile.service;


import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.stockPile.decorator.StockAddRequest;
import com.example.auth.stockPile.decorator.StockResponse;
import com.example.auth.stockPile.model.Stock;
import com.example.auth.stockPile.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final UserHelper userHelper;
    private final ModelMapper modelMapper;

    public StockServiceImpl(StockRepository stockRepository, NullAwareBeanUtilsBean nullAwareBeanUtilsBean, UserHelper userHelper, ModelMapper modelMapper) {
        this.stockRepository = stockRepository;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
        this.userHelper = userHelper;
        this.modelMapper = modelMapper;
    }

    @Override
    public StockResponse addStock(StockAddRequest stockAddRequest) {

        Stock stock = modelMapper.map(stockAddRequest, Stock.class);

        if (stockRepository.existsBySymbolAndSoftDeleteIsFalse(stockAddRequest.getSymbol())) {
            Stock existingStock = stockRepository.findBySymbolAndSoftDeleteIsFalse(stockAddRequest.getSymbol());
            StockResponse stockResponse = new StockResponse();
            stockResponse.setId(existingStock.getId());
            return stockResponse;
        }
        stockRepository.save(stock);

        StockResponse stockResponse = modelMapper.map(stock, StockResponse.class);

        return stockResponse;


    }

    @Override
    public StockResponse getStockById(String id) {
        Stock stock = stockById(id);
        StockResponse stockResponse = modelMapper.map(stock, StockResponse.class);
        return stockResponse;

    }

    @Override
    public void updateStock(String id, StockAddRequest stockAddRequest) throws NoSuchFieldException, IllegalAccessException {
        Stock stock = stockById(id);
        update(id, stockAddRequest);
        userHelper.difference(stock, stockAddRequest);
    }

    @Override
    public void deleteStockById(String id) {
        Stock stock = stockById(id);
        stock.setSoftDelete(true);
        stockRepository.save(stock);
    }

    @Override
    public List<StockResponse> getAllStock() {
        List<Stock> stocks = stockRepository.findAllBySoftDeleteFalse();
        List<StockResponse> stockResponses = new ArrayList<>();
        stocks.forEach(stock -> {
            StockResponse stockResponse = modelMapper.map(stock, StockResponse.class);
            stockResponses.add(stockResponse);
        });
        return stockResponses;
    }


    Stock stockById(String id) {
        return stockRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }


    private void update(String id, StockAddRequest stockAddRequest) {
        Stock stock = stockById(id);
        if (stockAddRequest.getSymbol() != null) {
            stock.setSymbol(stockAddRequest.getSymbol());
        }
        if (stockAddRequest.getDescription() != null) {
            stock.setDescription(stockAddRequest.getDescription());
        }
        if (stockAddRequest.getName() != null) {
            stock.setName(stockAddRequest.getName());
        }
        stockRepository.save(stock);

    }

}
