package com.example.auth.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.decorator.category.CategoryAddRequest;
import com.example.auth.decorator.category.CategoryResponse;
import com.example.auth.decorator.pagination.ItemFilter;
import com.example.auth.decorator.pagination.ItemSortBy;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.model.Category;
import com.example.auth.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    @Override
    public Page<CategoryResponse> getAllCategoryByPagination(ItemFilter filter, FilterSortRequest.SortRequest<ItemSortBy> sort, PageRequest pageRequest) {
        return categoryRepository.getAllCategoryByPagination(filter, sort, pageRequest);
    }


    public Category getById(String id) throws NotFoundException {
        return categoryRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_ID_NOT_FOUND));

    }
}


