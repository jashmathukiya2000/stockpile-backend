package com.example.auth.service;

import com.example.auth.decorator.category.CategoryAddRequest;
import com.example.auth.decorator.category.CategoryResponse;
import com.example.auth.decorator.pagination.ItemFilter;
import com.example.auth.decorator.pagination.ItemSortBy;
import com.example.auth.decorator.pagination.FilterSortRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse addOrUpdateCategory(String id, CategoryAddRequest categoryAddRequest);

    CategoryResponse getCategoryById(String id);

    List<CategoryResponse> getAllCategory();

    void deleteCategory(String id);


    Page<CategoryResponse> getAllCategoryByPagination(ItemFilter filter, FilterSortRequest.SortRequest<ItemSortBy> sort, PageRequest pageRequest);
}
