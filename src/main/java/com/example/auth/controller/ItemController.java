package com.example.auth.controller;

import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.GeneralHelper;
import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.*;
import com.example.auth.decorator.pagination.*;
import com.example.auth.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;
    private final GeneralHelper generalHelper;

    public ItemController(ItemService itemService, GeneralHelper generalHelper) {
        this.itemService = itemService;
        this.generalHelper = generalHelper;
    }

    @RequestMapping(name = "addItem", value = "/add", method = RequestMethod.POST)
    @Access(levels = Role.ADMIN)
    public DataResponse<ItemResponse> addItem(@RequestParam(required = false) String categoryId, @RequestBody ItemAddRequest itemAddRequest) throws InvocationTargetException, IllegalAccessException {
        DataResponse<ItemResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(itemService.addItem(categoryId, itemAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "updateItem", value = "/update", method = RequestMethod.POST)
    @Access (levels = Role.ADMIN)
    public DataResponse<ItemResponse> updateItem(@RequestParam String id, @RequestBody ItemAddRequest itemAddRequest) throws InvocationTargetException, IllegalAccessException {
        DataResponse<ItemResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(itemService.updateItem(id, itemAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.UPDATED_SUCCESSFULLY));
        return dataResponse;
    }


    @RequestMapping(name = "getItemById", value = "/get/{id}", method = RequestMethod.GET)
    @Access (levels = Role.ADMIN)
    public DataResponse<ItemResponse> getItemById(@RequestParam String id) {
        DataResponse<ItemResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(itemService.getItemById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }


    @RequestMapping(name = "getAllItems", value = "/getAll", method = RequestMethod.GET)
    @Access (levels = Role.ADMIN)
    public ListResponse<ItemResponse> geAllItems() {
        ListResponse<ItemResponse> listResponse = new ListResponse<>();
        listResponse.setData(itemService.getAllItems());
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }

    @RequestMapping(name = "deleteItemById", value = "/delete/{d}", method = RequestMethod.DELETE)
    @Access (levels = Role.ADMIN)
    public DataResponse<Object> deleteItemById(@RequestParam String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        itemService.deleteItemById(id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "getAllItemsByPagination", value = "get/all/pagination", method = RequestMethod.POST)
    @Access (levels = Role.ADMIN)
    public PageResponse<ItemResponse> getAllItemsByPagination(@RequestBody FilterSortRequest<ItemFilter, ItemSortBy> filterSortRequest) {
        PageResponse<ItemResponse> pageResponse = new PageResponse<>();
        Page<ItemResponse> itemResponses = itemService.getAllItemsByPagination(filterSortRequest.getFilter(),filterSortRequest.getSort(),generalHelper.getPagination(filterSortRequest.getPagination().getPage(),filterSortRequest.getPagination().getLimit()));
        pageResponse.setData(itemResponses);
        pageResponse.setStatus(Response.getOkResponse());
        return pageResponse;
    }


}
