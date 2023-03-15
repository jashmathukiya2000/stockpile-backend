package com.example.auth.controller;

import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.*;
import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.customer.CustomerAddRequest;
import com.example.auth.decorator.customer.CustomerLoginAddRequest;
import com.example.auth.decorator.customer.CustomerResponse;
import com.example.auth.decorator.pagination.CustomerFilter;
import com.example.auth.decorator.pagination.CustomerSortBy;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PageResponse;
import com.example.auth.model.Customer;
import com.example.auth.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

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
    public DataResponse<CustomerResponse> addCustomer(@RequestBody CustomerAddRequest customerAddRequest, @RequestParam Role role) {
        DataResponse<CustomerResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(customerService.addCustomer(customerAddRequest, role));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;

    }

    @RequestMapping(name = "login", value = "/login", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<CustomerResponse> login(@RequestBody CustomerLoginAddRequest customerLoginAddRequest) {
        DataResponse<CustomerResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(customerService.login(customerLoginAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.LOGIN_SUCCESSFULL));
        return dataResponse;
    }


    @RequestMapping(name = "getCustomerById", value = "/id", method = RequestMethod.GET)
    @Access(levels = Role.ADMIN)
    public DataResponse<CustomerResponse> getCustomerById(@RequestParam String id) {
        DataResponse<CustomerResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(customerService.getCustomerById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

    @RequestMapping(name = "getAllCustomer", value = "get/all/customer", method = RequestMethod.GET)
    @Access(levels = Role.ADMIN)
    public ListResponse<CustomerResponse> getAllCustomer() {
        ListResponse<CustomerResponse> listResponse = new ListResponse<>();
        listResponse.setData(customerService.getAllCustomer());
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }

    @RequestMapping(name = "deleteCustomer", value = "/delete/by/id", method = RequestMethod.DELETE)
    @Access(levels = Role.ADMIN)
    public DataResponse<Object> deleteCustomer(@RequestParam String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        customerService.deleteCustomer(id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

    @RequestMapping(name = "getAllCustomerByPagination", value = "/get/all/pagination", method = RequestMethod.POST)
    @Access(levels = Role.ADMIN)
    public PageResponse<Customer> getAllCustomerByPagination(@RequestBody FilterSortRequest<CustomerFilter, CustomerSortBy> filterSortRequest) {
        PageResponse<Customer> pageResponse = new PageResponse<>();
        Page<Customer> page = customerService.getAllCustomerByPagination(filterSortRequest.getFilter(), filterSortRequest.getSort(),
                generalHelper.getPagination(filterSortRequest.getPagination().getPage(), filterSortRequest.getPagination().getLimit()));
        pageResponse.setData(page);
        pageResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return pageResponse;
    }

    @RequestMapping(name = "otpVerification", value = "otp/verification", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Object> otpVerification(@RequestParam String otp, @RequestParam String email) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        customerService.otpVerification(otp, email);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OTP_VERIFIED));
        return dataResponse;

    }

    @RequestMapping(name = "getToken", value = "/get/token", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public TokenResponse<CustomerResponse> getToken(@RequestParam String id) {
        TokenResponse<CustomerResponse> tokenResponse = new TokenResponse<>();
        try {
            tokenResponse.setData(customerService.getToken(id));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        tokenResponse.setStatus(Response.getOkResponse(ResponseConstant.TOKEN_GENERATED_SUCCESSFULLY));

        return tokenResponse;
    }

    @RequestMapping(name = "getIdFromToken", value = "/token", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public TokenResponse<String> getIdFromToken(@RequestParam String token) {

        TokenResponse<String> tokenResponse = new TokenResponse<>();

        tokenResponse.setData(customerService.getIdFromToken(token));

        tokenResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));

        tokenResponse.setToken(token);

        return tokenResponse;
    }


    @RequestMapping(name = "logout", value = "logout/{id}", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Object> logout(@RequestParam String id) {

        DataResponse<Object> dataResponse = new DataResponse<>();

        customerService.logout(id);

        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.LOGOUT_SUCCESSFULLY));

        return dataResponse;
    }


    @RequestMapping(name = "forgetPassword", value = "/forget/password", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Object> forgetPassword(@RequestParam String email) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        customerService.forgetPassword(email);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OTP_SENT_SUCCESSFULLY));
        return dataResponse;
    }
//    @RequestMapping(name = "sendOtp", value = "/otp", method = RequestMethod.POST)
//    @Access(levels = Role.ANONYMOUS)
//    public DataResponse<Object> forgetPassword(@RequestParam String email,@RequestParam String otp) {
//        DataResponse<Object> dataResponse = new DataResponse<>();
//        customerService.sendOtp(email,otp);
//        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OTP_SENT_SUCCESSFULLY));
//        return dataResponse;
//    }

    @RequestMapping(name = "setPassword", value = "/set/password", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<Object> setPassword(@RequestParam String newPassword, @RequestParam String confirmPassword, @RequestParam String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        customerService.setPassword(newPassword, confirmPassword, id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.PASSWORD_UPDATED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "getEncryptPassword", value = "/encryptedPassword", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<String> getEncryptPassword(@RequestParam String id) {
        DataResponse<String> dataResponse = new DataResponse<>();
        dataResponse.setData(customerService.getEncryptPassword(id));
        dataResponse.setStatus(Response.getOkResponse());
        return dataResponse;
    }
//    @RequestMapping(name = "getCustomerAggregationById",value = "/customerName",method = RequestMethod.POST)
//    @Access (levels = Role.ANONYMOUS)
//    public ListResponse<ItemPurchaseAggregationResponse> getPurchaseDetailsByCustomerName(){
//        ListResponse<ItemPurchaseAggregationResponse> listResponse= new ListResponse<>();
//        listResponse.setData(customerService.getCustomerAggregationById());
//        listResponse.setStatus(Response.getOkResponse());
//        return listResponse;
//    }

}
