package com.example.auth.service;

import com.example.auth.decorator.ItemAddRequest;
import com.example.auth.decorator.ItemAggregationResponse;
import com.example.auth.decorator.ItemResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.ItemFilter;
import com.example.auth.decorator.pagination.ItemSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ItemService {
    ItemResponse addItem(String categoryId, ItemAddRequest itemAddRequest) throws InvocationTargetException, IllegalAccessException;

    ItemResponse getItemById(String id);

    List<ItemResponse> getAllItems();

    void deleteItemById(String id);

    Page<ItemResponse> getAllItemsByPagination(ItemFilter filter, FilterSortRequest.SortRequest<ItemSortBy> sort, PageRequest pageRequest);

    void removeItems(String id);

    ItemResponse updateItem(String id, ItemAddRequest itemAddRequest) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException;

    List<ItemAggregationResponse> getItemByAggregation();
}
