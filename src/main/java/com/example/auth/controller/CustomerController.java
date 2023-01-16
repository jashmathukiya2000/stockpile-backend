package com.example.auth.controller;

import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.GeneralHelper;
import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.DataResponse;
import com.example.auth.decorator.Response;
import com.example.auth.decorator.customer.CustomerLoginAddRequest;
import com.example.auth.decorator.customer.CustomerSignupAddRequest;
import com.example.auth.decorator.customer.CustomerSignupResponse;
import com.example.auth.decorator.pagination.CustomerFilter;
import com.example.auth.decorator.pagination.CustomerSortBy;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PageResponse;
import com.example.auth.model.Customer;
import com.example.auth.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

@RestController("login")
public class CustomerController {
    private final CustomerService customerService;
    private final GeneralHelper generalHelper;

    public CustomerController(CustomerService customerService, GeneralHelper generalHelper) {
        this.customerService = customerService;
        this.generalHelper = generalHelper;
    }

    @RequestMapping(name = "addCustomer", value = "/addCustomer", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<CustomerSignupResponse> addCustomer(@RequestBody CustomerSignupAddRequest customerSignupAddRequest, @RequestParam Role role) {
        DataResponse<CustomerSignupResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(customerService.addCustomer(customerSignupAddRequest, role));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;

    }

    @RequestMapping(name = "login", value = "/login/email", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<CustomerSignupResponse> login(@RequestBody CustomerLoginAddRequest customerLoginAddRequest) throws IllegalAccessException, NoSuchAlgorithmException, InvocationTargetException {
        DataResponse<CustomerSignupResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(customerService.login(customerLoginAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.LOGIN_SUCCESSFULL));
        return dataResponse;
    }

    @RequestMapping(name = "getAllCustomerByPagination", value = "/get/all/pagination", method = RequestMethod.POST)
    @Access(levels =Role.ADMIN)
    public PageResponse<Customer> getAllCustomerByPagination(@RequestBody FilterSortRequest<CustomerFilter, CustomerSortBy> filterSortRequest) {
        PageResponse<Customer> pageResponse = new PageResponse<>();
        Page<Customer> page = customerService.getAllCustomerByPagination(filterSortRequest.getFilter(), filterSortRequest.getSort(),
                generalHelper.getPagination(filterSortRequest.getPagination().getPage(), filterSortRequest.getPagination().getLimit()));
        pageResponse.setData(page);
        pageResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return pageResponse;
    }


}
