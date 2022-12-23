package com.example.auth.repository;

import com.example.auth.decorator.category.CategoryResponse;
import com.example.auth.decorator.pagination.CategoryFilter;
import com.example.auth.decorator.pagination.CategorySortBy;
import com.example.auth.decorator.pagination.FilterSortRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CategoryCustomRepository {
    Page<CategoryResponse> getAllCategoryByPagination(CategoryFilter filter, FilterSortRequest.SortRequest<CategorySortBy> sort, PageRequest pageRequest);

}
