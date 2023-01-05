package com.example.auth.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.decorator.ItemResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.ItemFilter;
import com.example.auth.decorator.pagination.ItemSortBy;
import com.example.auth.decorator.pagination.Pagination;
import com.example.auth.helper.ItemServiceImplTestGenerator;
import com.example.auth.repository.CategoryRepository;
import com.example.auth.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceImplTest {
    private final static String id = "id";
    private final static String categoryId = "id";
    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final ModelMapper modelMapper = ItemServiceImplTestGenerator.getModelMapper();
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean = mock(NullAwareBeanUtilsBean.class);
    public ItemService itemService = new ItemServiceImpl(categoryRepository, itemRepository, modelMapper, nullAwareBeanUtilsBean);


    @Test
    void testUpdateItem() throws InvocationTargetException, IllegalAccessException {

        //given
        var item = ItemServiceImplTestGenerator.getMockItem();
        var itemAddRequest = ItemServiceImplTestGenerator.getMockItemAddRequest();
        var itemResponse = ItemServiceImplTestGenerator.getMockItemResponse();
        when(itemRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(java.util.Optional.ofNullable(item));
        //when
        var actualData = itemService.updateItem(id, itemAddRequest);

        //then
        Assertions.assertEquals(itemResponse, actualData);
    }

    @Test
    void testAddItem() throws InvocationTargetException, IllegalAccessException {

        //given
        var category = ItemServiceImplTestGenerator.getMockCategory();
        var item = ItemServiceImplTestGenerator.getMockItem();
        var itemAddRequest = ItemServiceImplTestGenerator.getMockItemAddRequest();
        var itemResponse = ItemServiceImplTestGenerator.getMockItemResponse();
        when(categoryRepository.findByIdAndSoftDeleteIsFalse(categoryId)).thenReturn(category);

        //when
        var actualData = itemService.addItem(categoryId, itemAddRequest);

        //then
        Assertions.assertEquals(itemResponse, actualData);
    }

    @Test
    void testGetItemById() {

        //given
        var item = ItemServiceImplTestGenerator.getMockItem();
        var itemResponse = ItemServiceImplTestGenerator.getMockItemResponse();
        when(itemRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.ofNullable(item));

        //when
        var actualData = itemService.getItemById(id);

        //then
        Assertions.assertEquals(itemResponse, actualData);

    }

    @Test
    void testGetAllItem() {

        //given
        var item = ItemServiceImplTestGenerator.getMockListItem();
        var itemResponses = ItemServiceImplTestGenerator.getMockListResponse();
        when(itemRepository.findBySoftDeleteFalse()).thenReturn(item);

        //when
        var actualData = itemService.getAllItems();

        //then
        Assertions.assertEquals(itemResponses, actualData);
    }

    @Test
    void testDeleteItem() {

        //given
        var item = ItemServiceImplTestGenerator.getMockItem();
        when(itemRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.ofNullable(item));

        //when
        itemService.deleteItemById(id);

        //then
        verify(itemRepository, times(1)).findByIdAndSoftDeleteIsFalse(id);

    }

    @Test
    void testgetAllItemByPagination() {

        //given
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setId(itemFilter.getId());
        FilterSortRequest.SortRequest<ItemSortBy> sort = new FilterSortRequest.SortRequest<>();
        sort.setSortBy(ItemSortBy.ITEM_NAME);
        sort.setOrderBy(Sort.Direction.ASC);
        Pagination pagination = new Pagination();
        pagination.setPage(0);
        pagination.setLimit(2);
        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getLimit());
        var itemResponses = ItemServiceImplTestGenerator.getMockListResponse();
        Page<ItemResponse> page = new PageImpl<>(itemResponses);
        when(itemRepository.getAllItemsByPagination(itemFilter, sort, pageRequest)).thenReturn(page);


        //when
        var actualData = itemService.getAllItemsByPagination(itemFilter, sort, pageRequest);

        //then
        Assertions.assertEquals(page, actualData);

    }


}