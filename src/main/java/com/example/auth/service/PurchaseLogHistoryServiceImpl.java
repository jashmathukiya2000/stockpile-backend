package com.example.auth.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.InvalidRequestException;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.decorator.*;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import com.example.auth.model.Customer;
import com.example.auth.model.ExcelHelper;
import com.example.auth.model.Item;
import com.example.auth.model.PurchaseLogHistory;
import com.example.auth.repository.CustomerRepository;
import com.example.auth.repository.ItemRepository;
import com.example.auth.repository.PurchaseLogHistoryRepository;
import com.google.api.client.repackaged.com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;
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
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    public PurchaseLogHistoryServiceImpl(PurchaseLogHistoryRepository purchaseLogHistoryRepository, ModelMapper modelMapper, CustomerRepository customerRepository, NullAwareBeanUtilsBean nullAwareBeanUtilsBean, ItemRepository itemRepository, ItemService itemService) {
        this.purchaseLogHistoryRepository = purchaseLogHistoryRepository;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    @Override
    public PurchaseLogHistoryResponse addPurchaseLog(PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, String customerId, String itemName) {
        PurchaseLogHistory purchaseLogHistory = modelMapper.map(purchaseLogHistoryAddRequest, PurchaseLogHistory.class);
        Customer customer = getcustomerById(customerId);
        Item item = itemRepository.findByItemNameAndSoftDeleteIsFalse(itemName);
        if (purchaseLogHistory.getQuantity() <= item.getQuantity()) {
            purchaseLogHistory.setCustomerId(customer.getId());
            purchaseLogHistory.setItemName(item.getItemName());
            purchaseLogHistory.setPrice(item.getPrice());
            purchaseLogHistory.setCustomerName(customer.getName());
            purchaseLogHistory.setDiscountInPercent(item.getDiscountInPercent());
            findDiscountInRupee(purchaseLogHistory, itemName);
            purchaseLogHistory.setDate(currentDate());
            PurchaseLogHistoryResponse purchaseLogHistoryResponse = modelMapper.map(purchaseLogHistory, PurchaseLogHistoryResponse.class);
            purchaseLogHistoryRepository.save(purchaseLogHistory);
            setItemTotalPrice(itemName, purchaseLogHistory);
            return purchaseLogHistoryResponse;

        } else {
            throw new InvalidRequestException(MessageConstant.ITEM_QUANITY_OUT_OF_STOCK);
        }

    }

    public void setItemTotalPrice(String itemName, PurchaseLogHistory purchaseLogHistory) {
        Item item = itemRepository.findByItemNameAndSoftDeleteIsFalse(itemName);
        item.setQuantity(item.getQuantity() - purchaseLogHistory.getQuantity());
        item.setDiscountInRupee((item.getPrice() * item.getQuantity() * item.getDiscountInPercent()) / 100);
        item.setTotalPrice(item.getPrice() * item.getQuantity() - item.getDiscountInRupee());
        itemRepository.save(item);
    }

    @VisibleForTesting
    Date currentDate() {
        return new Date();
    }

    @Override
    public Object updatePurchaseLog(PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, String id) throws InvocationTargetException, IllegalAccessException {
        PurchaseLogHistory purchaseLogHistory = getItemPurchaseLogById(id);
        HashMap<String, String> changedProperties = new HashMap<>();
        updatePurchaseLogHistory(id, purchaseLogHistoryAddRequest);
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
        List<PurchaseLogHistory> purchaseLogHistoryList = new ArrayList<>();
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


    @Override
    public List<PurchaseAggregationResponse> getItemPurchaseDetailsByMonthYear() {
        return purchaseLogHistoryRepository.findItemPurchaseDetailsByMonthYear();

    }

    @Override
    public List<ItemPurchaseAggregationResponse> getPurchaseDetailsByCustomerName() {
        return purchaseLogHistoryRepository.getPurchaseDetailsByCustomerName();
    }

    @Override
    public Workbook getPurchaseLogByMonth(PurchaseLogFilter filter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pagination) throws InvocationTargetException, IllegalAccessException, JSONException {
        Page<PurchaseLogHistoryResponse> purchaseLogHistoryResponse = purchaseLogHistoryRepository.getPurchaseLogByMonth(filter, sort, pagination);
        List<PurchaseLogHistoryByMonthInExcel> purchaseLogHistoryByMonthInExcel = new ArrayList<>();
        List<PurchaseLogHistoryResponse> purchaseLogHistoryByMonthInExcels = purchaseLogHistoryResponse.getContent();
        for (PurchaseLogHistoryResponse logHistoryResponse : purchaseLogHistoryResponse) {
            PurchaseLogHistoryByMonthInExcel purchaseLogHistoryByMonthInExcel1 = new PurchaseLogHistoryByMonthInExcel();
            nullAwareBeanUtilsBean.copyProperties(purchaseLogHistoryByMonthInExcel1, logHistoryResponse);
            purchaseLogHistoryByMonthInExcel.add(purchaseLogHistoryByMonthInExcel1);
        }
        return ExcelUtils.createWorkbookFromData(purchaseLogHistoryByMonthInExcel, "Purchse details by month" + filter.getMonth());
    }

    @Override
    public Workbook getPurchaseDetailsByCustomer(PurchaseLogFilter filter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pageRequest) throws InvocationTargetException, IllegalAccessException, JSONException {
        HashMap<String, List<PurchaseLogExcelGenerator>> hashMap = new LinkedHashMap<>();
        Page<ItemPurchaseAggregationResponse> itemPurchaseAggregationResponse = purchaseLogHistoryRepository.getPurchaseDetailsByCustomer(filter, sort, pageRequest);
        List<ItemPurchaseAggregationResponse> list = itemPurchaseAggregationResponse.getContent();
        log.info("book Detail Excel Response:{}", list);

        for (ItemPurchaseAggregationResponse purchaseAggregationResponse : list) {
            List<PurchaseLogExcelGenerator> purchaseLogExcelGenerators = new ArrayList<>();
            for (ItemDetail itemDetail : purchaseAggregationResponse.getItemDetail()) {
                PurchaseLogExcelGenerator purchaseLogExcelGenerator = new PurchaseLogExcelGenerator();
                nullAwareBeanUtilsBean.copyProperties(purchaseLogExcelGenerator, itemDetail);
                purchaseLogExcelGenerators.add(purchaseLogExcelGenerator);
            }
            hashMap.put(purchaseAggregationResponse.get_id(), purchaseLogExcelGenerators);
        }
        log.info("hashMap:{}", hashMap);
        Workbook workbook = ExcelUtils.createWorkbookOnBookDetailsData(hashMap, "PurchaseDetails");
        return workbook;

    }

    Customer getcustomerById(String id) {
        return customerRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }

    PurchaseLogHistory getItemPurchaseLogById(String id) {
        return purchaseLogHistoryRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }

    public void updatePurchaseLogHistory(String id, PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest) {
        PurchaseLogHistory purchaseLogHistory = getItemPurchaseLogById(id);
        if (purchaseLogHistory != null) {
            if (purchaseLogHistoryAddRequest.getQuantity() > 0) {
                purchaseLogHistory.setQuantity(purchaseLogHistoryAddRequest.getQuantity());
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

    public void findDiscountInRupee(PurchaseLogHistory purchaseLogHistory, String itemName) {
        Item item = itemRepository.findByItemNameAndSoftDeleteIsFalse(itemName);
        purchaseLogHistory.setDiscountInRupee((purchaseLogHistory.getPrice() * purchaseLogHistory.getQuantity() * purchaseLogHistory.getDiscountInPercent()) / 100);
        purchaseLogHistory.setTotalPrice(item.getPrice() * purchaseLogHistory.getQuantity() - purchaseLogHistory.getDiscountInRupee());
    }


    public double findTotal(String customerId) {
        double total = 0;
        List<PurchaseLogHistory> purchaseLogHistory = purchaseLogHistoryRepository.findByCustomerIdAndSoftDeleteFalse(customerId);
        for (PurchaseLogHistory logHistory : purchaseLogHistory) {
            total += logHistory.getTotalPrice();
        }
        return total;
    }
}














