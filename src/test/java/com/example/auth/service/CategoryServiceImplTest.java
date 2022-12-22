package com.example.auth.service;

import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
import com.example.auth.helper.CategoryServiceImplGenerator;
import com.example.auth.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceImplTest {
    private static final String id = "id";
    private final CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    private final ModelMapper modelMapper = CategoryServiceImplGenerator.getModelMapper();
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean = Mockito.mock(NullAwareBeanUtilsBean.class);
    public CategoryService categoryService = new CategoryServiceImpl(categoryRepository, modelMapper, nullAwareBeanUtilsBean);


    @Test
    void TestUpdateCategory() {
        //given
        var category = CategoryServiceImplGenerator.MockCategory();
        var addCategory = CategoryServiceImplGenerator.MockAddCategory();
        var categoryresponse = CategoryServiceImplGenerator.MockCategoryResponse();
        when(categoryRepository.getByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        //when
        var actualData = categoryService.addOrUpdateCategory(id, addCategory);

        //then
        Assertions.assertEquals(categoryresponse, actualData);
    }

    @Test
    void testAddCategory() {
        //given
        var category = CategoryServiceImplGenerator.MockCategory();
        var addCategory = CategoryServiceImplGenerator.MockAddCategory();
        var categoryresponse = CategoryServiceImplGenerator.MockCategoryResponse();
        when(categoryRepository.getByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        //when
        var actualData = categoryService.addOrUpdateCategory(null, addCategory);

        //then
        Assertions.assertEquals(categoryresponse, actualData);
    }


    @Test
    void getCategoryById() {
        //given
        var category = CategoryServiceImplGenerator.MockCategory();
        var Categoryresponse = CategoryServiceImplGenerator.MockCategoryResponse();
        when(categoryRepository.getByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(category));

        //when
        var actualData = categoryService.getCategoryById(id);

        //then
        Assertions.assertEquals(Categoryresponse, actualData);
    }

    @Test
    void TestGetAllCategory() {
        //given
        var category = CategoryServiceImplGenerator.MockCategories();
        var Categoryresponse = CategoryServiceImplGenerator.getMockResponse();
        when(categoryRepository.findAllBySoftDeleteFalse()).thenReturn(category);

        //when
        var actualData = categoryService.getAllCategory();

        //then
        Assertions.assertEquals(Categoryresponse, actualData);
    }


    @Test
    void TestDeleteCategory() {
        //given
        var category = CategoryServiceImplGenerator.MockCategory();
        Mockito.when(categoryRepository.getByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(category));

        //when
        categoryService.deleteCategory(id);
        //then
        verify(categoryRepository, times(1)).getByIdAndSoftDeleteIsFalse((id));

    }


}
