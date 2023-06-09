package com.example.auth.helper;

import com.example.auth.commons.decorator.MonthConfig;
import com.example.auth.decorator.*;
import com.example.auth.model.Customer;
import com.example.auth.model.Item;
import com.example.auth.model.PurchaseLogHistory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import java.util.Date;
import java.util.List;

public class PurchaseLogHistoryServiceImplTestGenerator {
    private final static String id = "id";
    private final static String customerId = "id";

    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static PurchaseLogHistory mockPurchaseLogHistory(Date date) {
        return PurchaseLogHistory
                .builder()
//                .id(customerId)
                .softDelete(false)
                .id(id)
                .itemName("mouse")
                .customerId(customerId)
                .customerName("dency")
                .quantity(5)
                .discountInRupee(3000)
                .totalPrice(57000)
                .discountInPercent(5)
                .date(date)
                .price(12000)
                .build();
    }

    public static List<PurchaseLogHistory> mockListPurchaseLogHistory() {
        return List.of(PurchaseLogHistory
                .builder()
                .itemName("mouse")
                .id(id)
                .customerId(customerId)
                .quantity(5)
                .price(12000)
                .softDelete(false)
                .discountInPercent(5)
                .build());
    }


    public static PurchaseLogHistoryAddRequest mockPurchaseLogHistoryAddRequest() {
        return PurchaseLogHistoryAddRequest
                .builder()
                .quantity(5)
                .build();

    }

    public static PurchaseLogHistoryResponse mockPurchaseLogHistoryResponse(Date date,String id) {
        return PurchaseLogHistoryResponse
                .builder()
                .id(id)
                .itemName("mouse")
                .customerId(customerId)
                .customerName("dency")
                .quantity(5)
                .discountInRupee(3000.0)
                .totalPrice(57000.0)
                .discountInPercent(5)
                .date(date)
                .price(12000)
                .build();

    }

//    public static PurchaseLogHistoryResponse mockPurchaseHistoryResponse() {
//        return PurchaseLogHistoryResponse.builder()
//                .itemName("mouse")
//                .customerId(customerId)
//                .quantity(5)
//                .discountInRupee(3000)
//                .totalPrice(57000)
//                .date(new Date())
//                .discountInPercent(5)
//                .price(12000)
//                .build();
//
//    }

    public static List<PurchaseLogHistoryResponse> mockListPurchaseLogHistoryResponse() {
        return List.of(PurchaseLogHistoryResponse
                .builder()
                .customerId(customerId)
                .itemName("mouse")
                .id(id)
                .quantity(5)
                .price(12000)
                .discountInPercent(5)
                .build());

    }

    public static Customer mockCustomer() {
        return Customer.builder()
                .id(customerId)
                .name("dency")
                .softDelete(false)
                .build();

    }
    public static Item getItem(){
        return Item.builder().itemName("mouse")
                .id(id)
                .categoryId(customerId)
                .discountInRupee(3000)
                .discountInPercent(5)
                .price(12000)
                .quantity(5)
                .totalPrice(57000)
                .build();
    }


    public static List<ItemPurchaseAggregationResponse> getItemPurchaseAggregationResponse(){
        return List.of(ItemPurchaseAggregationResponse.builder()
                ._id(id)
                .itemDetail(getItemDetail())
                .build());
    }



    public static  PurchaseLogExcelGenerator getPurchaseLogExcelGenerator(){
        return PurchaseLogExcelGenerator.builder()
                .itemName("mouse")
                .build();
    }

    public static MainDateFilter getMainDateFilter(){
        return MainDateFilter.builder()
                .dateFilters(getPurchaseLogHistoryFilter())
                .build();
    }

    public static List<PurchaseLogHistoryFilter> getPurchaseLogHistoryFilter(){
        return List.of(PurchaseLogHistoryFilter.builder().build());
    }

    public static MonthConfig getMonthConfig(){
        return MonthConfig.builder().build();
    }

    public static List<GetByMonthAndYear> getByMonthAndYear(){
        return List.of(GetByMonthAndYear.builder()
                .itemDetail(getItemDetails())
                .totalItem(4)
                .build());
    }

    public static List<ItemDetail> getItemDetail(){
        return List.of(ItemDetail.builder()
                .itemName("mouse")
                .build());
    }

    public static List<ItemDetails> getItemDetails(){
        return List.of(ItemDetails.builder()
                .itemName("mouse")
                .build());
    }

    public static List<PurchaseLogHistoryFilter> getPurchaseHistoryFilter(){
        return List.of(PurchaseLogHistoryFilter.builder()
                .last(false)
                .month(1)
                .year(2023)
                .build());
    }









}
