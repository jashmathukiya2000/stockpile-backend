package com.example.auth.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.InvalidRequestException;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.decorator.category.CategoryAddRequest;
import com.example.auth.decorator.category.CategoryResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.ItemFilter;
import com.example.auth.decorator.pagination.ItemSortBy;
import com.example.auth.model.Category;
import com.example.auth.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final ItemService itemService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, NullAwareBeanUtilsBean nullAwareBeanUtilsBean, ItemService itemService) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
        this.itemService = itemService;
    }

    public CategoryResponse addCategory(CategoryAddRequest categoryAddRequest) {
        Category category = modelMapper.map(categoryAddRequest, Category.class);
        category.setDate(new Date());
        CategoryResponse categoryResponse = modelMapper.map(categoryAddRequest, CategoryResponse.class);
        checkValidation(categoryAddRequest);
        categoryRepository.save(category);
        return categoryResponse;
    }

    @Override
    public CategoryResponse updateCategory(String id, CategoryAddRequest categoryAddRequest) {
        Category category = getById(id);
        category.setDate(new Date());
        category.setCategoryName(categoryAddRequest.getCategoryName());
        CategoryResponse categoryResponse = modelMapper.map(categoryAddRequest, CategoryResponse.class);
        categoryRepository.save(category);
        return categoryResponse;
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = getById(id);
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
        return categoryResponse;
    }

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
        itemService.removeItems(id);
        category.setSoftDelete(true);
        categoryRepository.save(category);
    }


    public void checkValidation(CategoryAddRequest categoryAddRequest) {
        if (categoryAddRequest.getCategoryName().isEmpty()) {
            throw new InvalidRequestException(MessageConstant.NAME_MUST_NOT_BE_NULL);
        }
        if (categoryRepository.existsBycategoryNameAndSoftDeleteIsFalse(categoryAddRequest.getCategoryName())) {
            throw new InvalidRequestException(MessageConstant.ALREADY_EXIST);
        }

    }

    @Override
    public Page<CategoryResponse> getAllCategoryByPagination(ItemFilter filter, FilterSortRequest.SortRequest<ItemSortBy> sort, PageRequest pageRequest) {
        return categoryRepository.getAllCategoryByPagination(filter, sort, pageRequest);
    }


    public Category getById(String id) throws NotFoundException {
        return categoryRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_ID_NOT_FOUND));

    }

}


