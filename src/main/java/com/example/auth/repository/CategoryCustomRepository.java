package com.example.auth.repository;

import com.example.auth.decorator.category.CategoryResponse;
import com.example.auth.decorator.pagination.ItemFilter;
import com.example.auth.decorator.pagination.ItemSortBy;
import com.example.auth.decorator.pagination.FilterSortRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CategoryCustomRepository {
    Page<CategoryResponse> getAllCategoryByPagination(ItemFilter filter, FilterSortRequest.SortRequest<ItemSortBy> sort, PageRequest pageRequest);

}
