package com.example.auth.controller;

import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.decorator.*;
import com.example.auth.decorator.pagination.*;
import com.example.auth.decorator.user.*;
import com.example.auth.service.ResultService;
import com.example.auth.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/users")

public class UserController {
    private final UserService userService;
    private final ResultService resultService;

    public UserController(UserService userService, ResultService resultService) {
        this.userService = userService;
        this.resultService = resultService;
    }

    @RequestMapping(name = "addOrUpdateUser", value = "/add", method = RequestMethod.POST)
    public DataResponse<UserResponse> addOrUpdateUser(@RequestParam(required = false) String id, @RequestBody UserAddRequest userAddRequest) throws InvocationTargetException, IllegalAccessException {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userService.addOrUpdateUser(id, userAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }


    @RequestMapping(name = "getAllUser", value = "/getAll", method = RequestMethod.GET)
    public ListResponse<UserResponse> getAllUser() {
        ListResponse<UserResponse> listResponse = new ListResponse<>();
        listResponse.setData(userService.getAllUser());
        listResponse.setStatus(Response.getOkResponse());
        return listResponse;
    }


    @RequestMapping(name = "getUserById", value = "/user/{id}", method = RequestMethod.POST)
    public DataResponse<UserResponse> getUserById(@PathVariable String id) throws InvocationTargetException, IllegalAccessException {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userService.getUser(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

    @RequestMapping(name = "deleteUser", value = "/delete/{id}", method = RequestMethod.DELETE)
    public DataResponse<Object> deleteUser(@PathVariable String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        userService.deleteUser(id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "getUserByAge", value = "/age", method = RequestMethod.POST)
    public ListResponse<UserResponse> getUserByAge(@RequestBody UserFilter userFilter) {
        ListResponse<UserResponse> listResponse = new ListResponse<>();
        listResponse.setData(userService.getUserByAge(userFilter));
        listResponse.setStatus(Response.getOkResponse());
        return listResponse;
    }

    @RequestMapping(name = "getUserBySalaryAggregation", value = "/salary", method = RequestMethod.POST)
    public ListResponse<UserAggregationResponse> getUserBySalary(@RequestBody UserFilter userFilter) {
        ListResponse<UserAggregationResponse> listResponse = new ListResponse<>();
        listResponse.setData(userService.getUserBySalary(userFilter));
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }


    @RequestMapping(name = "addResult", value = "/addResult/{id}", method = RequestMethod.POST)
    public DataResponse<UserResponse> addResult(@PathVariable String id, @RequestBody Result result) throws InvocationTargetException, IllegalAccessException {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(resultService.addResult(id, result));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;
    }

    @RequestMapping(name = "getBySpi", value = "/spi", method = RequestMethod.GET)
    public ListResponse<UserSpiResponse> getBySpi(@RequestParam double spi) {
        ListResponse<UserSpiResponse> dataResponse = new ListResponse<>();
        dataResponse.setData(resultService.getBySpi(spi));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;
    }

    @RequestMapping(name = "getAllUserByPagination", value = "get/all/pagination",method = RequestMethod.POST)
    public PageResponse<UserResponse> getAllUserByPagination(@RequestBody FilterSortRequest<UserFilterData, UserSortBy> filterSortRequest){
        PageResponse<UserResponse> pageResponse=new PageResponse<>();
        UserFilterData filter= filterSortRequest.getFilter();
        FilterSortRequest.SortRequest<UserSortBy> sort=filterSortRequest.getSort();
        Pagination pagination= filterSortRequest.getPagination();
        PageRequest pageRequest=PageRequest.of(pagination.getPage(), pagination.getLimit());
        Page<UserResponse> userResponses=userService.getAllUserByPagination(filter,sort,pageRequest);
        pageResponse.setData(userResponses);
        pageResponse.setStatus(Response.getOkResponse());
        return pageResponse;
    }


}