package com.example.auth.controller;

import com.example.auth.common.config.constant.ResponseConstant;
import com.example.auth.decorator.*;
import com.example.auth.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("categroy")
public class CategoryController {

    private final CategoryService categoryService;
    //value -- endpoint check
    //provide response --> response class
    @RequestMapping(name = "addOrUpdateCategory", value = "/addOrUpdate", method = RequestMethod.POST)
    public DataResponse<CategoryResponse> addOrUpdateCategory(@RequestParam(required = false) String id, @RequestBody CategoryAddRequest categoryAddRequest) {
        DataResponse<CategoryResponse> dataResponse = new DataResponse<>();
   dataResponse.setData(  categoryService.addOrUpdateCategory(id,categoryAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }


    //add description in response

    @RequestMapping(name = "getCategoryById", value = "/{id}", method = RequestMethod.GET)
    public DataResponse<CategoryResponse> getCategoryById(@PathVariable String id) {
        DataResponse<CategoryResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(categoryService.getCategoryById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }


   //end point name change
    //methodName change
   //add description in response

    @RequestMapping(name = "getAllCategory", value = "/getAllCategroy", method = RequestMethod.GET)
    public ListResponse<CategoryResponse> getAllCategory() {
        ListResponse<CategoryResponse> listResponse = new ListResponse<>();
        listResponse.setData(categoryService.getAllCategory());
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));

        return listResponse;
    }

    @RequestMapping(name = "deleteCategory", value = "delete/{id}", method = RequestMethod.DELETE)
    public DataResponse<Object> deleteCategory(@PathVariable String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        categoryService.deleteCategory(id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));
        return dataResponse;
    }
}
