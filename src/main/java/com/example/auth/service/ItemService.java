package com.example.auth.service;

import com.example.auth.decorator.ItemAddRequest;
import com.example.auth.decorator.ItemResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.ItemFilter;
import com.example.auth.decorator.pagination.ItemSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ItemService {
    ItemResponse addOrUpdateItem(String categoryId, ItemAddRequest itemAddRequest, String id) throws InvocationTargetException, IllegalAccessException;

    ItemResponse getItemById(String id);

    List<ItemResponse> getAllItems();

    void deleteItemById(String id);

    Page<ItemResponse> getAllItemsByPagination(ItemFilter filter, FilterSortRequest.SortRequest<ItemSortBy> sort, PageRequest pageRequest);
}
