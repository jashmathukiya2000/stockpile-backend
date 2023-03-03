package com.example.auth.stockPile.service;


import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.stockPile.decorator.StockAddRequest;
import com.example.auth.stockPile.decorator.StockFilter;
import com.example.auth.stockPile.decorator.StockResponse;
import com.example.auth.stockPile.decorator.StockSortBy;
import com.example.auth.stockPile.model.Stock;
import com.example.auth.stockPile.model.Subscriber;
import com.example.auth.stockPile.model.UserData;
import com.example.auth.stockPile.repository.StockRepository;
import com.example.auth.stockPile.repository.SubscriberRepository;
import com.example.auth.stockPile.repository.UserDataRepository;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final UserHelper userHelper;
    private final ModelMapper modelMapper;
    private final UserData userData;
    private final UserDataServiceImpl userDataService;
    private final UserDataRepository userDataRepository;
    private final Subscriber subscriber;
    private final SubscriberRepository subscriberRepository;


    public StockServiceImpl(StockRepository stockRepository, NullAwareBeanUtilsBean nullAwareBeanUtilsBean, UserHelper userHelper, ModelMapper modelMapper, UserData userData, UserDataServiceImpl userDataService, UserDataRepository userDataRepository, Subscriber subscriber, SubscriberRepository subscriberRepository) {
        this.stockRepository = stockRepository;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
        this.userHelper = userHelper;
        this.modelMapper = modelMapper;
        this.userData = userData;
        this.userDataService = userDataService;


        this.userDataRepository = userDataRepository;
        this.subscriber = subscriber;
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public StockResponse addStock(StockAddRequest stockAddRequest) {
        Stock stock = modelMapper.map(stockAddRequest, Stock.class);

        if (stockRepository.existsBySymbolAndSoftDeleteIsFalse(stockAddRequest.getSymbol())) {
            Stock existingStock = getBySymbol(stockAddRequest.getSymbol());
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

    @Override
    public Page<StockResponse> getAllStockByPagination(StockFilter filter, FilterSortRequest.SortRequest<StockSortBy> sort, PageRequest pagination) {
        return stockRepository.getAllStockByPagination(filter, sort, pagination);
    }

    @Override
    public String getStockSubscription(String symbol, String userId) {
        Stock stock = getBySymbol(symbol);
        UserData user = userDataService.userById(userId);
        List<String> subscribers = stock.getSubscribers();
        user.setSubscribe(true);
        String subscribesId = user.getId();
        if (!subscribers.contains(subscribesId)) {
            subscribers.add(subscribesId);
        }
        stock.setSubscribers(subscribers);
        subscriber.setStockid(stock.getId());
        subscriber.setCreatedOn(new Date());
        subscriber.setUserId(subscribesId);
        subscriberRepository.save(subscriber);
        stockRepository.save(stock);
        userDataRepository.save(user);

        return subscribesId;
    }

        @Override
        public Map<String, List<Stock>> allSubscribers() {
            return stockRepository.findAllBySoftDeleteFalse().stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(stock ->
                        stock.getSymbol()));
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
    public Stock getBySymbol(String symbol) {
        return stockRepository.findBySymbolAndSoftDeleteIsFalse(symbol).orElseThrow(() -> new NotFoundException(MessageConstant.SYMBOL_NOT_FOUND));
    }




}
