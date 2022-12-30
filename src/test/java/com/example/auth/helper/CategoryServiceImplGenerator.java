package com.example.auth.helper;

import com.example.auth.decorator.category.CategoryAddRequest;
import com.example.auth.decorator.category.CategoryResponse;
import com.example.auth.model.Category;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Date;
import java.util.List;

public class CategoryServiceImplGenerator {
    private static final String id = "id";

    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }


    public static Category MockCategory() {
        return Category.builder()
                .categoryName("computer")
                .build();
    }

    public static CategoryAddRequest MockAddCategory() {
        return CategoryAddRequest.builder()
                .categoryName("computer")
                .build();
    }

    public static CategoryResponse MockCategoryResponse() {
        return CategoryResponse.builder()
               .categoryName("computer")
                .build();
    }

    public static List<Category> MockCategories() {
        return List.of(Category.builder()
                .categoryName("computer")
                .build());

    }

    public static List<CategoryResponse> getMockResponse() {
        return List.of(CategoryResponse
                .builder()
                .categoryName("computer")

                .build());
    }
}
