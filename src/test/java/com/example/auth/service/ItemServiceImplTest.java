package com.example.auth.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.helper.ItemServiceImplTestGenerator;
import com.example.auth.repository.CategoryRepository;
import com.example.auth.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

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
        var actualData = itemService.addOrUpdateItem(null, itemAddRequest, id);

        //then
        Assertions.assertEquals(itemResponse, actualData);
    }

    @Test
    void testAddItem() throws InvocationTargetException, IllegalAccessException {

        //given
        var item = ItemServiceImplTestGenerator.getMockItem();
        var itemAddRequest = ItemServiceImplTestGenerator.getMockItemAddRequest();
        var itemResponse = ItemServiceImplTestGenerator.getMockItemResponse();
        when(itemRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.ofNullable(item));

        //when
        var actualData = itemService.addOrUpdateItem(categoryId, itemAddRequest, id);

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
        when(itemRepository.findAllBySoftDeleteFalse()).thenReturn(item);

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


}