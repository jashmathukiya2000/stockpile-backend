package com.example.auth.repository;

import com.example.auth.decorator.ItemAggregationResponse;
import com.example.auth.decorator.ItemResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.ItemFilter;
import com.example.auth.decorator.pagination.ItemSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ItemCustomRepository {
    Page<ItemResponse> getAllItemsByPagination(ItemFilter filter, FilterSortRequest.SortRequest<ItemSortBy> sort, PageRequest pageRequest);
    public List<ItemAggregationResponse> getItemByAggregation();
}
