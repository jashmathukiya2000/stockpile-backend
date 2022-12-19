package com.example.auth.helper;

import com.example.auth.decorator.CategoryAddRequest;
import com.example.auth.decorator.CategoryResponse;
import com.example.auth.decorator.UserResponse;
import com.example.auth.model.Category;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;

public class CategoryServiceImplGenerator {
    private static final String id="id";
    public  static ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }


    public static Category MockCategory(){
        return  Category.builder()
                .itemName("noodles")
                .price(100)
                .quantity(5)
                .build();
    }

    public static CategoryAddRequest MockAddCategory(){
        return CategoryAddRequest.builder()
                .itemName("noodles")
                .price(100)
                .quantity(5)
                .build();
    }
    public static CategoryResponse MockCategoryResponse(){
        return CategoryResponse.builder()
                .itemName("noodles")
                .price(100)
                .quantity(5)
                .build();
    }
    public static List<Category> MockCategories(){
        return List.of(Category.builder()
                .id(id)
                .softDelete(false)
                .itemName("noodles")
                .price(100)
                .quantity(5)
                .build());

    }
    public static List<CategoryResponse> getMockResponse(){
        return  List.of(CategoryResponse
                .builder()
                .itemName("noodles")
                .price(100)
                .quantity(5)
                . build());
    }
}
