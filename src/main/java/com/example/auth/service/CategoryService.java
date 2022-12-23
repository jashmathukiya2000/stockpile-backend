package com.example.auth.service;

import com.example.auth.decorator.category.CategoryAddRequest;
import com.example.auth.decorator.category.CategoryResponse;
import com.example.auth.decorator.pagination.CategoryFilter;
import com.example.auth.decorator.pagination.CategorySortBy;
import com.example.auth.decorator.pagination.FilterSortRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    CategoryResponse addOrUpdateCategory(String id, CategoryAddRequest categoryAddRequest);

    CategoryResponse getCategoryById(String id);

    List<CategoryResponse> getAllCategory();

    void deleteCategory(String id);


    Page<CategoryResponse> getAllCategoryByPagination(CategoryFilter filter, FilterSortRequest.SortRequest<CategorySortBy> sort, PageRequest pageRequest);
}
