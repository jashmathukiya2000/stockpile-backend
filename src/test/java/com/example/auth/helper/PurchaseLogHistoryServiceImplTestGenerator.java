package com.example.auth.helper;

import com.example.auth.decorator.PurchaseLogHistoryAddRequest;
import com.example.auth.decorator.PurchaseLogHistoryResponse;
import com.example.auth.model.Customer;
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

    public static PurchaseLogHistory mockPurchaseLogHistory( ) {
        return PurchaseLogHistory
                .builder()
                .itemName("mouse")
                .quantity(5)
                .id(id)
                .discountInRupee(3000)
                .totalPrice(57000)
                .customerId(customerId)
                .price(12000)
                .date(new Date())
                .discountInPercent(5)
                .softDelete(false)
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
                .itemName("mouse")
                .quantity(5)
                .discountInPercent(5)
                .price(12000)
                .build();

    }

    public static PurchaseLogHistoryResponse mockPurchaseLogHistoryResponse() {
        return PurchaseLogHistoryResponse.builder()
                .itemName("mouse")
                .id(id)
                .customerId(customerId)
                .quantity(5)
                .discountInRupee(3000)
                .totalPrice(57000)
                .date(new Date())
                .discountInPercent(5)
                .price(12000)
                .build();

    }

    public static PurchaseLogHistoryResponse mockPurchaseHistoryResponse() {
        return PurchaseLogHistoryResponse.builder()
                .itemName("mouse")
                .customerId(customerId)
                .quantity(5)
                .discountInRupee(3000)
                .totalPrice(57000)
                .date(new Date())
                .discountInPercent(5)
                .price(12000)
                .build();

    }

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
                .build();

    }


}
