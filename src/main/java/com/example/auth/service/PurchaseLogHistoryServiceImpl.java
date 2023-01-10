package com.example.auth.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.decorator.PurchaseLogHistoryAddRequest;
import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import com.example.auth.model.Customer;
import com.example.auth.model.ExcelHelper;
import com.example.auth.model.PurchaseLogHistory;
import com.example.auth.repository.CustomerRepository;
import com.example.auth.repository.PurchaseLogHistoryRepository;
import com.google.api.client.repackaged.com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
@Slf4j
public class PurchaseLogHistoryServiceImpl implements PurchaseLogHistoryService {
    private final PurchaseLogHistoryRepository purchaseLogHistoryRepository;

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;


    public PurchaseLogHistoryServiceImpl(PurchaseLogHistoryRepository purchaseLogHistoryRepository, ModelMapper modelMapper, CustomerRepository customerRepository, NullAwareBeanUtilsBean nullAwareBeanUtilsBean) {
        this.purchaseLogHistoryRepository = purchaseLogHistoryRepository;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;

        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;

    }

    @Override
    public PurchaseLogHistoryResponse addPurchaseLog(PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, String customerId) {
        PurchaseLogHistory purchaseLogHistory = modelMapper.map(purchaseLogHistoryAddRequest, PurchaseLogHistory.class);
        Customer customer = getcustomerById(customerId);
        purchaseLogHistory.setCustomerId(customer.getId());
        purchaseLogHistory.setCustomerName(customer.getName());
        findDiscountInRupee(purchaseLogHistory);
        purchaseLogHistory.setDate(currentDate());
        PurchaseLogHistoryResponse purchaseLogHistoryResponse = modelMapper.map(purchaseLogHistory, PurchaseLogHistoryResponse.class);
        purchaseLogHistoryRepository.save(purchaseLogHistory);
        return purchaseLogHistoryResponse;
    }

    @VisibleForTesting
    Date currentDate() {
        return new Date();
    }

    @Override
    public Object updatePurchaseLog(PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, String id) throws InvocationTargetException, IllegalAccessException {
        PurchaseLogHistory purchaseLogHistory = getItemPurchaseLogById(id);
        HashMap<String, String> changedProperties = new HashMap<>();
        updatePurchaseLog(id, purchaseLogHistoryAddRequest);
        difference(purchaseLogHistory, purchaseLogHistoryAddRequest, changedProperties);
        return null;
    }

    @Override
    public PurchaseLogHistoryResponse getPurchaseLogById(String id) {
        PurchaseLogHistory purchaseLogHistory = getItemPurchaseLogById(id);
        PurchaseLogHistoryResponse purchaseLogHistoryResponse = modelMapper.map(purchaseLogHistory, PurchaseLogHistoryResponse.class);
        return purchaseLogHistoryResponse;
    }

    @Override
    public List<PurchaseLogHistoryResponse> getAllPurchaseLog() {
        List<PurchaseLogHistory> purchaseLogHistory = purchaseLogHistoryRepository.findAllBySoftDeleteFalse();
        List<PurchaseLogHistoryResponse> list = new ArrayList<>();
        purchaseLogHistory.forEach(purchaseLogHistory1 -> {
            PurchaseLogHistoryResponse purchaseLogHistoryResponse = modelMapper.map(purchaseLogHistory1, PurchaseLogHistoryResponse.class);
            list.add(purchaseLogHistoryResponse);
        });
        return list;
    }

    @Override
    public Object deletePurchaseLogById(String id) {
        PurchaseLogHistory purchaseLogHistory = getItemPurchaseLogById(id);
        purchaseLogHistory.setSoftDelete(true);
        purchaseLogHistoryRepository.save(purchaseLogHistory);
        return null;
    }

    @Override
    public Page<PurchaseLogHistoryResponse> getAllPurchaseLogByPagination(PurchaseLogFilter purchaseLogFilter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pageRequest) {
        return purchaseLogHistoryRepository.getAllPurchaseLogByPagination(purchaseLogFilter, sort, pageRequest);
    }

    @Override
    public List<PurchaseLogHistory> findById(String customerId) {
        List<PurchaseLogHistory> purchaseLogHistory = purchaseLogHistoryRepository.findByCustomerIdAndSoftDeleteFalse(customerId);
        List<PurchaseLogHistory> purchaseLogHistoryList= new ArrayList<>();
        for (PurchaseLogHistory logHistory : purchaseLogHistory) {
               logHistory.setTotal(findTotal(customerId));

        }
        return purchaseLogHistory;
    }


    @Override
    public void save(MultipartFile file) {
        try {
            List<PurchaseLogHistory> purchaseLogHistoryList = ExcelHelper.excelTopurchaseLogHistoryList(file.getInputStream());
            purchaseLogHistoryRepository.saveAll(purchaseLogHistoryList);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data :" + e.getMessage());
        }
    }


    Customer getcustomerById(String id) {
        return customerRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }
    PurchaseLogHistory getItemPurchaseLogById(String id) {
        return purchaseLogHistoryRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }

    public void updatePurchaseLog(String id, PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest) {
        PurchaseLogHistory purchaseLogHistory = getItemPurchaseLogById(id);
        if (purchaseLogHistory != null) {
            if (purchaseLogHistoryAddRequest.getItemName() != null) {
                purchaseLogHistory.setItemName(purchaseLogHistoryAddRequest.getItemName());
            }
            if (purchaseLogHistoryAddRequest.getPrice() > 0) {
                purchaseLogHistory.setPrice(purchaseLogHistoryAddRequest.getPrice());
                findDiscountInRupee(purchaseLogHistory);
            }

            if (purchaseLogHistoryAddRequest.getQuantity() > 0) {
                purchaseLogHistory.setQuantity(purchaseLogHistoryAddRequest.getQuantity());
                findDiscountInRupee(purchaseLogHistory);
            }
            if (purchaseLogHistoryAddRequest.getDiscountInPercent() > 0) {
                purchaseLogHistory.setDiscountInPercent(purchaseLogHistoryAddRequest.getDiscountInPercent());
                findDiscountInRupee(purchaseLogHistory);

            }
            purchaseLogHistoryRepository.save(purchaseLogHistory);
        }


    }

    public void difference(PurchaseLogHistory purchaseLogHistory, PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, HashMap<String, String> changedProperties) throws InvocationTargetException, IllegalAccessException {
        PurchaseLogHistory purchaseLogHistory1 = new PurchaseLogHistory();
        nullAwareBeanUtilsBean.copyProperties(purchaseLogHistory1, purchaseLogHistoryAddRequest);
        purchaseLogHistory1.setId(purchaseLogHistory.getId());
        for (Field field : purchaseLogHistory.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(purchaseLogHistory);
            Object value1 = field.get(purchaseLogHistory1);
            if (value != null && value1 != null) {
                if (!Objects.equals(value, value1)) {
                    changedProperties.put(field.getName(), value1.toString());
                }
            }
        }
    }


    public void findDiscountInRupee(PurchaseLogHistory purchaseLogHistory) {
        purchaseLogHistory.setDiscountInRupee((purchaseLogHistory.getPrice() * purchaseLogHistory.getQuantity() * purchaseLogHistory.getDiscountInPercent()) / 100);
        purchaseLogHistory.setTotalPrice(purchaseLogHistory.getPrice() * purchaseLogHistory.getQuantity() - purchaseLogHistory.getDiscountInRupee());

    }


    public double findTotal(String customerId){
        double total = 0;
        List<PurchaseLogHistory> purchaseLogHistory = purchaseLogHistoryRepository.findByCustomerIdAndSoftDeleteFalse(customerId);
        for (PurchaseLogHistory logHistory : purchaseLogHistory) {
            total += logHistory.getTotalPrice();
        }
        return total;
    }
}









