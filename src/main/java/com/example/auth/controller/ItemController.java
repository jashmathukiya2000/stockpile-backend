package com.example.auth.controller;

import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.decorator.*;
import com.example.auth.decorator.pagination.*;
import com.example.auth.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(name = "addItem",value = "/add",method = RequestMethod.POST )
    public DataResponse<ItemResponse> addItem(@RequestParam(required = false) String categoryId,@RequestBody ItemAddRequest itemAddRequest) throws InvocationTargetException, IllegalAccessException {
        DataResponse<ItemResponse> dataResponse=new DataResponse<>();
        dataResponse.setData(itemService.addItem(categoryId,itemAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }
  @RequestMapping(name = "updateItem",value = "/update",method = RequestMethod.POST )
    public DataResponse<ItemResponse> updateItem(@RequestParam String id,@RequestBody ItemAddRequest itemAddRequest) throws InvocationTargetException, IllegalAccessException {
        DataResponse<ItemResponse> dataResponse=new DataResponse<>();
        dataResponse.setData(itemService.updateItem(id,itemAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.UPDATED_SUCCESSFULLY));
        return dataResponse;
    }


    @RequestMapping(name = "getItemById",value = "/get/{id}",method = RequestMethod.GET)
    public DataResponse<ItemResponse> getItemById(@RequestParam String id){
        DataResponse<ItemResponse> dataResponse= new DataResponse<>();
        dataResponse.setData(itemService.getItemById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }


    @RequestMapping(name = "getAllItems",value = "/getAll",method = RequestMethod.GET)
    public ListResponse<ItemResponse> geAllItems(){
        ListResponse<ItemResponse> listResponse= new ListResponse<>();
        listResponse.setData(itemService.getAllItems());
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }

    @RequestMapping(name = "deleteItemById",value = "/delete/{d}",method = RequestMethod.DELETE)
    public DataResponse<Object> deleteItemById(@RequestParam String id){
        DataResponse<Object> dataResponse= new DataResponse<>();
        itemService.deleteItemById(id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));
        return dataResponse;
    }
    @RequestMapping(name = "getAllItemsByPagination", value = "get/all/pagination",method = RequestMethod.POST)
    public PageResponse<ItemResponse> getAllItemsByPagination(@RequestBody FilterSortRequest<ItemFilter, ItemSortBy> filterSortRequest){
        PageResponse<ItemResponse> pageResponse=new PageResponse<>();
        ItemFilter filter= filterSortRequest.getFilter();
        FilterSortRequest.SortRequest<ItemSortBy> sort=filterSortRequest.getSort();
        Pagination pagination= filterSortRequest.getPagination();
        PageRequest pageRequest=PageRequest.of(pagination.getPage(), pagination.getLimit());
        Page<ItemResponse> itemResponses=itemService.getAllItemsByPagination(filter,sort,pageRequest);
        pageResponse.setData(itemResponses);
        pageResponse.setStatus(Response.getOkResponse());
        return pageResponse;
    }


}
