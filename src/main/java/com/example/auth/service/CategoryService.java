package com.example.auth.service;

import com.example.auth.decorator.CategoryAddRequest;
import com.example.auth.decorator.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse addOrUpdateCategory(String id, CategoryAddRequest categoryAddRequest);

    CategoryResponse getCategoryById(String id);

    List<CategoryResponse> getAllCategory();

    void deleteCategory(String id);
}
