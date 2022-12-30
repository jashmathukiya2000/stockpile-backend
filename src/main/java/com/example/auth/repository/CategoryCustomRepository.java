package com.example.auth.repository;

import com.example.auth.decorator.category.CategoryResponse;
import com.example.auth.decorator.pagination.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CategoryCustomRepository {
    Page<CategoryResponse> getAllCategoryByPagination(CategoryFilter filter, FilterSortRequest.SortRequest<CategorySortBy> sort, PageRequest pageRequest);

}
