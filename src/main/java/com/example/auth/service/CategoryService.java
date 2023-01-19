package com.example.auth.service;

import com.example.auth.decorator.category.CategoryAddRequest;
import com.example.auth.decorator.category.CategoryResponse;
import com.example.auth.decorator.pagination.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CategoryService {
    CategoryResponse addCategory(CategoryAddRequest categoryAddRequest) throws InvocationTargetException, IllegalAccessException;

    CategoryResponse getCategoryById(String id);

    List<CategoryResponse> getAllCategory();

    void deleteCategory(String id);


    Page<CategoryResponse> getAllCategoryByPagination(CategoryFilter filter, FilterSortRequest.SortRequest<CategorySortBy> sort, PageRequest pageRequest);

    CategoryResponse updateCategory(String id, CategoryAddRequest categoryAddRequest);
}
