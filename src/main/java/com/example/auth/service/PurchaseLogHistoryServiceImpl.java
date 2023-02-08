package com.example.auth.service;

import com.example.auth.commons.FileLoader;
import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.decorator.ExcelUtils;
import com.example.auth.commons.decorator.MonthConfig;
import com.example.auth.commons.decorator.TemplateParser;
import com.example.auth.commons.exception.InvalidRequestException;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.decorator.*;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import com.example.auth.model.*;
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
import org.joda.time.DateTime;

import java.io.IOException;
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
    private final UserHelper userHelper;


    public PurchaseLogHistoryServiceImpl(PurchaseLogHistoryRepository purchaseLogHistoryRepository, ModelMapper modelMapper, CustomerRepository customerRepository, NullAwareBeanUtilsBean nullAwareBeanUtilsBean, ItemRepository itemRepository, ItemService itemService, UserHelper userHelper) {
        this.purchaseLogHistoryRepository = purchaseLogHistoryRepository;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
        this.itemRepository = itemRepository;
        this.itemService = itemService;
        this.userHelper = userHelper;


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

    @VisibleForTesting
    Item getQuantity() {
        Item item = new Item();
        item.getQuantity();
        return item;
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
    public void updatePurchaseLog(PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, String id) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        PurchaseLogHistory purchaseLogHistory = getItemPurchaseLogById(id);
        updatePurchaseLogHistory(id, purchaseLogHistoryAddRequest);
        userHelper.difference(purchaseLogHistory, purchaseLogHistoryAddRequest);

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
    public List<PurchaseAggregationResponse> getItemPurchaseDetailsByMonthYear() throws JSONException {
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
        list.forEach(purchaseAggregationResponse -> {
            List<PurchaseLogExcelGenerator> purchaseLogExcelGenerators = new ArrayList<>();
            purchaseAggregationResponse.getItemDetail().forEach(itemDetail -> {
                PurchaseLogExcelGenerator purchaseLogExcelGenerator = new PurchaseLogExcelGenerator();
                try {
                    nullAwareBeanUtilsBean.copyProperties(purchaseLogExcelGenerator, itemDetail);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                purchaseLogExcelGenerators.add(purchaseLogExcelGenerator);
            });
            hashMap.put(purchaseAggregationResponse.get_id(), purchaseLogExcelGenerators);
        });

        getPurchaseHistory();
        Workbook workbook = ExcelUtils.createWorkbookOnBookDetailsData(hashMap, "PurchaseDetails");
        return workbook;
    }

    @Override
    public Page<MainDateFilter> getDateFilters(PurchaseLogFilter filter, FilterSortRequest.SortRequest<PurchaseLogSortBy> sort, PageRequest pagination, MainDateFilter mainDateFilter) throws JSONException {
        MonthConfig monthConfig = new MonthConfig();
        mainDateFilter = getMainDateFilter(monthConfig, filter);
        System.out.println("mainDateFilter:" + mainDateFilter);
        return purchaseLogHistoryRepository.getDateFilters(filter, sort, pagination, mainDateFilter);
    }


    @VisibleForTesting
    public MainDateFilter getMainDateFilter(MonthConfig monthConfig, PurchaseLogFilter filter) {
        List<PurchaseLogHistoryFilter> dateFilters = new LinkedList<>(getDateFilters(monthConfig, filter));
        log.info("MainDateFilter:{}", dateFilters);
        return MainDateFilter.builder().dateFilters(dateFilters).build();

    }


    private List<PurchaseLogHistoryFilter> getDateFilters(MonthConfig monthConfig, PurchaseLogFilter filter) {
        List<PurchaseLogHistoryFilter> purchaseLogHistoryFilters = new ArrayList<>();
        DateTime dateTime = new DateTime().withMonthOfYear(filter.getMonth()).withYear(filter.getYear()).withDayOfMonth(1);
        log.info("dateTime:{}", dateTime);
        purchaseLogHistoryFilters.add(getDateFilter(filter.getMonth(), filter.getYear(), false));
        int monthDifference = monthConfig.getGetGetAccountingDashBoardMonthDifference();
        if (monthDifference > 0) {
            for (int i = 1; i <= monthDifference; i++) {
                boolean last = monthDifference == i;
                DateTime updatedDateTime = dateTime.minusMonths(i);
                purchaseLogHistoryFilters.add(getDateFilter(updatedDateTime.getMonthOfYear(), updatedDateTime.getYear(), last));
            }
        }
        log.info("purchaseLogHistoryFilters:{}", purchaseLogHistoryFilters);
        return purchaseLogHistoryFilters;


    }


    private PurchaseLogHistoryFilter getDateFilter(int month, int year, boolean last) {
        return PurchaseLogHistoryFilter.builder().month(month).year(year).last(last).build();
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
                findTotal(id);
            }
            purchaseLogHistoryRepository.save(purchaseLogHistory);
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

    public String getPurchaseHistory() {
        PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest = new PurchaseLogHistoryAddRequest();

        TemplateParser<PurchaseLogHistoryAddRequest> templateParser = new TemplateParser<>();

        String url = templateParser.compileTemplate(FileLoader.loadHtmlTemplateOrReturnNull("purchaseLogHistory"), purchaseLogHistoryAddRequest);

        log.info("url :{}", url);

        return url;
    }
}














