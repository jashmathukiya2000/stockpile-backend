package com.example.auth.service;
import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.Pagination;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import com.example.auth.helper.PurchaseLogHistoryServiceImplTestGenerator;
import com.example.auth.repository.CustomerRepository;
import com.example.auth.repository.PurchaseLogHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.lang.reflect.InvocationTargetException;
import static org.mockito.Mockito.*;

@SpringBootTest
class PurchaseLogHistoryResponseServiceImplTest {
    private final static String id = "id";
    private final static String customerId = "id";
    private final PurchaseLogHistoryRepository purchaseLogHistoryRepository = mock(PurchaseLogHistoryRepository.class);
    private final ModelMapper modelMapper = PurchaseLogHistoryServiceImplTestGenerator.getModelMapper();
    private final CustomerRepository customerRepository = mock(CustomerRepository.class);
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean = mock(NullAwareBeanUtilsBean.class);
    PurchaseLogHistoryServiceImpl purchaseLogHistoryService = new PurchaseLogHistoryServiceImpl(purchaseLogHistoryRepository, modelMapper, customerRepository, nullAwareBeanUtilsBean);

    @Test
    void testAddPurchaseLogHistory() {
        //given
        var customer = PurchaseLogHistoryServiceImplTestGenerator.mockCustomer();
        var purchaseLogHistory = PurchaseLogHistoryServiceImplTestGenerator.mockPurchaseLogHistory();
        var purchaseLogAddRequest = PurchaseLogHistoryServiceImplTestGenerator.mockPurchaseLogHistoryAddRequest();
        var purchaseLogResponse = PurchaseLogHistoryServiceImplTestGenerator.mockPurchaseHistoryResponse();
        when(customerRepository.findByIdAndSoftDeleteIsFalse(customerId)).thenReturn(java.util.Optional.ofNullable(customer));
        when(purchaseLogHistoryRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(java.util.Optional.ofNullable(purchaseLogHistory));
        purchaseLogHistoryRepository.save(purchaseLogHistory);

        //when
        var actualData = purchaseLogHistoryService.addPurchaseLog(purchaseLogAddRequest, customerId);

        //then
        Assertions.assertEquals(purchaseLogResponse, actualData);

    }


    @Test
    void testUpdatePurchaseLogHistory() throws InvocationTargetException, IllegalAccessException {
        //given
        var purchaseLogHistory = PurchaseLogHistoryServiceImplTestGenerator.mockPurchaseLogHistory();
        var purchaseLogAddRequest = PurchaseLogHistoryServiceImplTestGenerator.mockPurchaseLogHistoryAddRequest();
        var purchaseLogResponse = PurchaseLogHistoryServiceImplTestGenerator.mockPurchaseLogHistoryResponse();
        when(purchaseLogHistoryRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(java.util.Optional.ofNullable(purchaseLogHistory));

        //when
        var actualData = purchaseLogHistoryService.updatePurchaseLog(purchaseLogAddRequest, id);

        //then
        verify(purchaseLogHistoryRepository, times(2)).findByIdAndSoftDeleteIsFalse(id);

    }

    @Test
    void testGetPurchaseLogHistory() {
        //given
        var purchaseLogHistory = PurchaseLogHistoryServiceImplTestGenerator.mockPurchaseLogHistory();
        var purchaseLogResponse = PurchaseLogHistoryServiceImplTestGenerator.mockPurchaseLogHistoryResponse();
        when(purchaseLogHistoryRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(java.util.Optional.ofNullable(purchaseLogHistory));

        //when
        var actualData = purchaseLogHistoryService.getPurchaseLogById(id);

        //then
        Assertions.assertEquals(purchaseLogResponse, actualData);
    }


    @Test
    void testGetAllPurchaseLog() {
        //given
        var purchaseLogHistory = PurchaseLogHistoryServiceImplTestGenerator.mockListPurchaseLogHistory();
        var purchaseLogResponse = PurchaseLogHistoryServiceImplTestGenerator.mockListPurchaseLogHistoryResponse();
        when(purchaseLogHistoryRepository.findAllBySoftDeleteFalse()).thenReturn(purchaseLogHistory);

        //when
        var actualData = purchaseLogHistoryService.getAllPurchaseLog();

        //then
        Assertions.assertEquals(purchaseLogResponse, actualData);

    }

    @Test
    void testDeletepurchaseLog() {
        //given
        var purchaseLogHistory = PurchaseLogHistoryServiceImplTestGenerator.mockPurchaseLogHistory();
        when(purchaseLogHistoryRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(java.util.Optional.ofNullable(purchaseLogHistory));

        //when
        purchaseLogHistoryService.deletePurchaseLogById(id);

        //then
        verify(purchaseLogHistoryRepository, times(1)).findByIdAndSoftDeleteIsFalse(id);


    }

    @Test
    void testGetAllPurchaseLogByPagination() {
        PurchaseLogFilter filter = new PurchaseLogFilter();
        filter.setIds(filter.getIds());
        FilterSortRequest.SortRequest<PurchaseLogSortBy> sort = new FilterSortRequest.SortRequest<>();
        sort.setSortBy(PurchaseLogSortBy.ITEM_NAME);
        sort.setOrderBy(Sort.Direction.ASC);

        Pagination pagination = new Pagination();
        pagination.setPage(1);
        pagination.setLimit(5);
        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getLimit());

        var purchaseLogResponse = PurchaseLogHistoryServiceImplTestGenerator.mockListPurchaseLogHistoryResponse();
        Page<PurchaseLogHistoryResponse> page = new PageImpl<>(purchaseLogResponse);
        when(purchaseLogHistoryRepository.getAllPurchaseLogByPagination(filter, sort, pageRequest)).thenReturn(page);

        //when
        var actualData = purchaseLogHistoryService.getAllPurchaseLogByPagination(filter, sort, pageRequest);

        //then
        Assertions.assertEquals(page, actualData);
    }
}