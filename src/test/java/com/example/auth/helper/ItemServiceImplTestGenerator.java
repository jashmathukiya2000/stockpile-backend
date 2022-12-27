package com.example.auth.helper;

import com.example.auth.decorator.ItemAddRequest;
import com.example.auth.decorator.ItemResponse;
import com.example.auth.model.Item;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;

public class ItemServiceImplTestGenerator {
    private static final String  id="id";
    private static final String  categoryId="id";
    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static Item getMockItem(){
       return Item.builder().itemName("monitor").price(500).quantity(2).build();

    }
    public static ItemResponse getMockItemResponse(){
        return ItemResponse.builder().itemName("monitor").price(500).quantity(2).build();
    }

    public static ItemAddRequest getMockItemAddRequest(){
        return ItemAddRequest.builder().itemName("monitor").price(500).quantity(2).build();
    }

    public static List<ItemResponse> getMockListResponse(){
        return List.of(ItemResponse.builder().itemName("monitor").price(500).quantity(2).id(id).build());
    }
    public static List<Item> getMockListItem(){
        return List.of(Item.builder().id(id).itemName("monitor").price(500).quantity(2).build());
    }


}
