package com.example.auth.service;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
import com.example.auth.helper.CategoryServiceImplGenerator;
import com.example.auth.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

import java.util.Optional;

@SpringBootTest
public class CategoryServiceImplTest {
    private static final String id="id";
    private final CategoryRepository categoryRepository= Mockito.mock(CategoryRepository.class);
    private final ModelMapper modelMapper= CategoryServiceImplGenerator.getModelMapper();
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean=Mockito.mock(NullAwareBeanUtilsBean.class);
    public CategoryService categoryService= new CategoryServiceImpl(categoryRepository,modelMapper,nullAwareBeanUtilsBean);


    @Test
    void TestAddOrUpdateCategory(){
        //given
        var category=CategoryServiceImplGenerator.MockCategory();
        var addCategory=CategoryServiceImplGenerator.MockAddCategory();
        var response=CategoryServiceImplGenerator.MockCategoryResponse();
       when(categoryRepository.getByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(category));

        //when
        var actualData=categoryService.addOrUpdateCategory(id,addCategory);

        //then
        Assertions.assertEquals(response,actualData);
    }


    @Test
     void getCategoryById(){
        //given
        var category=CategoryServiceImplGenerator.MockCategory();
        var response=CategoryServiceImplGenerator.MockCategoryResponse();
        when(categoryRepository.getByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(category));

        //when
        var actualData=categoryService.getCategoryById(id);

        //then

        Assertions.assertEquals(response,actualData);
    }

    @Test
    void TestGetAllCategory(){

        //given
        var category=CategoryServiceImplGenerator.MockCategories();
        var response=CategoryServiceImplGenerator.getMockResponse();
        when(categoryRepository.findAllBySoftDeleteFalse()).thenReturn(category);

        //when
        var actualData=categoryService.getAllCategory();

        //then
        Assertions.assertEquals(response,actualData);
    }


    @Test
    void TestDeleteCategory(){

        //given
        var category=CategoryServiceImplGenerator.MockCategory();
        Mockito.when(categoryRepository.getByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(category));

        //when
        categoryService.deleteCategory(id);
        //then
     verify(categoryRepository,times(1)).getByIdAndSoftDeleteIsFalse(eq(id));

    }


}
