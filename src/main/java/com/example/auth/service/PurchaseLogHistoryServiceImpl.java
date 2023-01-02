package com.example.auth.service;

import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.decorator.PurchaseLogHistoryAddRequest;
import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.model.Customer;
import com.example.auth.model.Item;
import com.example.auth.model.PurchaseLogHistory;
import com.example.auth.repository.CustomerRepository;
import com.example.auth.repository.ItemRepository;
import com.example.auth.repository.PurchaseLogHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PurchaseLogHistoryServiceImpl implements PurchaseLogHistoryService {
    private final PurchaseLogHistoryRepository purchaseLogHistoryRepository;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;



    public PurchaseLogHistoryServiceImpl(PurchaseLogHistoryRepository purchaseLogHistoryRepository, ModelMapper modelMapper, CustomerService customerService, CustomerRepository customerRepository, ItemService itemService, ItemRepository itemRepository) {
        this.purchaseLogHistoryRepository = purchaseLogHistoryRepository;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.customerRepository = customerRepository;

    }

    @Override
    public PurchaseLogHistoryResponse addPurchaseLog(PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, String customerId) {
        PurchaseLogHistory purchaseLogHistory  = modelMapper.map(purchaseLogHistoryAddRequest, PurchaseLogHistory.class);
        Customer  customer=getcustomerById(customerId);
        purchaseLogHistory.getId();
        purchaseLogHistory.setCustomerId(customer.getId());
        purchaseLogHistory.setDiscountInRupee((purchaseLogHistory.getPrice()* purchaseLogHistory.getQuantity() * purchaseLogHistory.getDiscountInPercent())/100);
        purchaseLogHistory.setTotalPrice( purchaseLogHistory.getPrice()* purchaseLogHistory.getQuantity() - purchaseLogHistory.getDiscountInRupee());
        purchaseLogHistory.setDate(new Date());
        PurchaseLogHistoryResponse purchaseLogHistoryResponse=modelMapper.map(purchaseLogHistory,PurchaseLogHistoryResponse.class);
        purchaseLogHistoryRepository.save(purchaseLogHistory);


        return purchaseLogHistoryResponse;
    }

    Customer getcustomerById(String id){
      return   customerRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(()-> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }









}
