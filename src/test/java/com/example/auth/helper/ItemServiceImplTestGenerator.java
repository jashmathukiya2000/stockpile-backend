package com.example.auth.helper;

import com.example.auth.decorator.ItemAddRequest;
import com.example.auth.decorator.ItemResponse;
import com.example.auth.model.Category;
import com.example.auth.model.Item;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ItemServiceImplTestGenerator {
    private static final String id = "id";
    private static final String categoryId = "id";

    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static Item getMockItem() {
        return Item.builder()
                .id(id)
                .categoryId(categoryId)
                .itemName("monitor")
                .price(500)
                .quantity(2)
                .totalPrice(1000)
                .date(new Date())
                .build();
    }

    public static ItemResponse getMockItemResponse() {
        return ItemResponse.builder()
                .itemName("monitor")
                .categoryId(categoryId)
                .price(500)
                .quantity(2)
                .date(new Date())
                .totalPrice(1000.0)
                .build();
    }

    public static ItemAddRequest getMockItemAddRequest() {
        return ItemAddRequest.builder()
                .itemName("monitor")
                .price(500)
                .quantity(2).build();
    }

    public static List<ItemResponse> getMockListResponse() {
        return List.of(ItemResponse
                .builder()
                .itemName("monitor")
                .price(500)
                .quantity(2)
                .build());
    }

    public static List<Item> getMockListItem() {
        return List.of(Item.builder()
                .itemName("monitor")
                .price(500)
                .quantity(2).build());
    }

    public static Optional<Category> getMockCategory() {
        return Optional.of(Category.builder()
                .id(categoryId)
                .build());
    }


}
