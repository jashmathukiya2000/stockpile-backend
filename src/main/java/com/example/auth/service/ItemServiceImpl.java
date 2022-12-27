package com.example.auth.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.decorator.ItemAddRequest;
import com.example.auth.decorator.ItemResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.ItemFilter;
import com.example.auth.decorator.pagination.ItemSortBy;
import com.example.auth.model.Category;
import com.example.auth.model.Item;
import com.example.auth.repository.CategoryRepository;
import com.example.auth.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    public ItemServiceImpl(CategoryRepository categoryRepository, ItemRepository itemRepository, ModelMapper modelMapper, NullAwareBeanUtilsBean nullAwareBeanUtilsBean) {
        this.categoryRepository = categoryRepository;

        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
    }

    @Override
    public ItemResponse addOrUpdateItem(String categoryId,ItemAddRequest itemAddRequest, String id) throws InvocationTargetException, IllegalAccessException {
            if (id!=null) {
                Item item=getById(id);
                item.setItemName(itemAddRequest.getItemName());
                item.setPrice(itemAddRequest.getPrice());
                item.setQuantity(itemAddRequest.getQuantity());
                ItemResponse itemResponse = modelMapper.map(itemAddRequest, ItemResponse.class);
                itemRepository.save(item);
                return itemResponse;
            } else {

                Item item =modelMapper.map(itemAddRequest, Item.class);
             Category category=getCategoryModel(categoryId);
                item.setCategoryId(category.getId());
                ItemResponse itemResponse = modelMapper.map(item, ItemResponse.class);
                itemRepository.save(item);
                return itemResponse;
            }
        }



    @Override
    public ItemResponse getItemById(String id) {
        Item item = getById(id);
        ItemResponse itemResponse = modelMapper.map(item, ItemResponse.class);
        return itemResponse;
    }



    @Override
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAllBySoftDeleteFalse();
        List<ItemResponse> itemResponses= new ArrayList<>();
      items.forEach(item -> {
          ItemResponse itemResponse=modelMapper.map(item,ItemResponse.class);
          itemResponses.add(itemResponse);
      });

        return itemResponses;
    }

    @Override
    public void deleteItemById(String id) {
       Item item=getById(id);
       item.setSoftDelete(true);
       itemRepository.save(item);
       ;
    }

    @Override
    public Page<ItemResponse> getAllItemsByPagination(ItemFilter filter, FilterSortRequest.SortRequest<ItemSortBy> sort, PageRequest pageRequest) {
        return itemRepository.getAllItemsByPagination(filter,sort,pageRequest);
    }

    public Category getCategoryModel(String categoryId){
        return categoryRepository.findByIdAndSoftDeleteIsFalse(categoryId).orElseThrow(()-> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }

    public Item getById(String id) {
        return itemRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.ID_NOT_FOUND));

    }
}
