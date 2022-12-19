package com.example.auth.service;

import com.example.auth.common.config.constant.MessageConstant;
import com.example.auth.common.config.exception.NotFoundException;
import com.example.auth.decorator.CategoryAddRequest;
import com.example.auth.decorator.CategoryResponse;
import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
import com.example.auth.model.Category;
import com.example.auth.repository.CategoryRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, NullAwareBeanUtilsBean nullAwareBeanUtilsBean) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
    }


    @Override
    public CategoryResponse addOrUpdateCategory(String id, CategoryAddRequest categoryAddRequest) {
        if (id != null) {
            Category category = getById(id);
            category.setPrice(categoryAddRequest.getPrice());
            category.setItemName(categoryAddRequest.getItemName());
            category.setQuantity(categoryAddRequest.getQuantity());
            CategoryResponse categoryResponse = modelMapper.map(categoryAddRequest, CategoryResponse.class);
            categoryRepository.save(category);
            return categoryResponse;
        } else {
            Category category = modelMapper.map(categoryAddRequest, Category.class);
            CategoryResponse categoryResponse = modelMapper.map(categoryAddRequest, CategoryResponse.class);
             categoryRepository.save(category);
            return categoryResponse;
        }
    }


    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = getById(id);
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
        return categoryResponse;
    }

    @SneakyThrows
    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> category = categoryRepository.findAllBySoftDeleteFalse();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        category.forEach(category1 -> {
            CategoryResponse categoryResponse = modelMapper.map(category1, CategoryResponse.class);
            categoryResponses.add(categoryResponse);
        });
        return categoryResponses;
    }

    @Override
    public void deleteCategory(String id) {
        Category category = getById(id);
        category.setSoftDelete(true);
        categoryRepository.save(category);
    }

    public Category getById(String id) throws NotFoundException {
        return categoryRepository.getByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_ID_NOT_FOUND));

    }
}


