package com.example.auth.controller;

import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.*;
import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.*;
import com.example.auth.decorator.pagination.*;
import com.example.auth.decorator.user.*;
import com.example.auth.service.ResultService;
import com.example.auth.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/users")

public class UserController {
    private final UserService userService;
    private final ResultService resultService;
    private final GeneralHelper generalHelper;

    public UserController(UserService userService, ResultService resultService, GeneralHelper generalHelper) {
        this.userService = userService;
        this.resultService = resultService;
        this.generalHelper = generalHelper;
    }

    @RequestMapping(name = "addUser", value = "/add", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)
    public DataResponse<UserResponse> addUser(@RequestBody UserAddRequest userAddRequest) {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userService.addUser(userAddRequest));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;

    }

    @RequestMapping(name = "updateUser", value = "/update", method = RequestMethod.POST)
    @Access(levels = Role.ADMIN)
    public DataResponse<UserResponse> updateUser(@RequestParam String id, @RequestBody UserAddRequest userAddRequest) {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        userService.updateUser(id, userAddRequest);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.UPDATED_SUCCESSFULLY));
        return dataResponse;
    }


    @RequestMapping(name = "getAllUser", value = "/getAll", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public ListResponse<UserResponse> getAllUser() {
        ListResponse<UserResponse> listResponse = new ListResponse<>();
        listResponse.setData(userService.getAllUser());
        listResponse.setStatus(Response.getOkResponse());
        return listResponse;
    }


    @RequestMapping(name = "getUserById", value = "/user/{id}", method = RequestMethod.POST)
    @Access(levels = {Role.ADMIN})
    public DataResponse<UserResponse> getUserById(@PathVariable String id) {
        DataResponse<UserResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(userService.getUser(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }


    @RequestMapping(name = "deleteUser", value = "/delete/{id}", method = RequestMethod.DELETE)
    @Access(levels = Role.ADMIN)
    public DataResponse<Object> deleteUser(@PathVariable String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        userService.deleteUser(id);
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "getUserByAge", value = "/age", method = RequestMethod.POST)
    @Access(levels = Role.ADMIN)
    public ListResponse<UserResponse> getUserByAge(@RequestBody UserFilter userFilter) {
        ListResponse<UserResponse> listResponse = new ListResponse<>();
        listResponse.setData(userService.getUserByAge(userFilter));
        listResponse.setStatus(Response.getOkResponse());
        return listResponse;
    }

    @RequestMapping(name = "getUserBySalaryAggregation", value = "/salary", method = RequestMethod.POST)
    @Access(levels = Role.ADMIN)
    public ListResponse<UserAggregationResponse> getUserBySalary(@RequestBody UserFilter userFilter) {
        ListResponse<UserAggregationResponse> listResponse = new ListResponse<>();
        listResponse.setData(userService.getUserBySalary(userFilter));
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }


    @RequestMapping(name = "addResult", value = "/addResult/{id}", method = RequestMethod.POST)
    @Access(levels = {Role.ADMIN})
    public DataResponse<UserResponse> addResult(@PathVariable String id, @RequestBody Result result) {
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

    @RequestMapping(name = "getAllUserByPagination", value = "get/all/pagination", method = RequestMethod.POST)
    @Access(levels = Role.ADMIN)
    public PageResponse<UserResponse> getAllUserByPagination(@RequestBody FilterSortRequest<UserFilterData, UserSortBy> filterSortRequest) {
        PageResponse<UserResponse> pageResponse = new PageResponse<>();
        Page<UserResponse> userResponses = userService.getAllUserByPagination(filterSortRequest.getFilter(), filterSortRequest.getSort(), generalHelper.getPagination(filterSortRequest.getPagination().getPage(), filterSortRequest.getPagination().getLimit()));
        pageResponse.setData(userResponses);
        pageResponse.setStatus(Response.getOkResponse());
        return pageResponse;
    }

    @RequestMapping(name = "getUserByMaxSpi", value = "/get{id}", method = RequestMethod.POST)
    @Access(levels = Role.ADMIN)
    public ListResponse<MaxSpiResponse> getUserByMaxSpi(@PathVariable String id) {
        ListResponse<MaxSpiResponse> listResponse = new ListResponse<>();
        listResponse.setData(userService.getUserByMaxSpi(id));
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }

    @RequestMapping(name = "getToken", value = "/get/token", method = RequestMethod.GET)
    @Access(levels = Role.ANONYMOUS)
    public TokenResponse<UserResponse> getToken(@RequestParam String id) {
        TokenResponse<UserResponse> tokenResponse = new TokenResponse<>();
        tokenResponse.setData(userService.getToken(id));
        tokenResponse.setStatus(Response.getOkResponse(ResponseConstant.TOKEN_GENERATED_SUCCESSFULLY));
        return tokenResponse;
    }

    @RequestMapping(name = "getIdFromToken", value = "/{id}", method = RequestMethod.GET)

    @Access(levels = Role.ANONYMOUS)

    public TokenResponse<String> getIdFromToken(@RequestParam String token) {

        TokenResponse<String> tokenResponse = new TokenResponse<>();

        tokenResponse.setData(userService.getIdFromToken(token));

        tokenResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));

        tokenResponse.setToken(token);

        return tokenResponse;
    }

    @RequestMapping(name = "getExpirationDateFromToken", value = "/expirationDate/token", method = RequestMethod.POST)

    @Access(levels = Role.ANONYMOUS)

    public TokenResponse<Date> getExpirationDateFromToken(@RequestParam String token) {

        TokenResponse<Date> tokenResponse = new TokenResponse<>();

        tokenResponse.setData(userService.getExpirationDateFromToken(token));

        tokenResponse.setStatus(Response.getOkResponse());

        tokenResponse.setToken(token);

        return tokenResponse;
    }

    @RequestMapping(name = "isTokenExpired", value = "/tokenExpired", method = RequestMethod.POST)

    @Access(levels = Role.ANONYMOUS)

    public TokenResponse<Boolean> isTokenExpired(@RequestParam String token) {

        TokenResponse<Boolean> tokenResponse = new TokenResponse<>();

        tokenResponse.setData(userService.isTokenExpired(token));

        tokenResponse.setStatus(Response.getOkResponse());

        tokenResponse.setToken(token);

        return tokenResponse;
    }

    @RequestMapping(name = "getAllUserInExcel", value = "/getAll/excel", method = RequestMethod.POST)

    @Access(levels = Role.ANONYMOUS)

    public ResponseEntity<Resource> getAllUserInExcel() {

        Workbook workbook = userService.getAllUserInExcel();

        assert workbook != null;

        ByteArrayResource resource = ExcelUtils.getBiteResourceFromWorkbook(workbook);

        return ResponseEntity.ok()

                .contentLength(resource.contentLength())

                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "user_purchased_bookDetail_xlsx" + "\"")

                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))

                .body(resource);
    }


    @RequestMapping(name = "getUserDetailsByResultSpi", value = "/result/spi", method = RequestMethod.POST)

    @Access(levels = Role.ANONYMOUS)

    public ResponseEntity<Resource> getUserDetailsByResultSpi(@RequestBody FilterSortRequest<UserFilterData, UserSortBy> filterSortRequest) {

        UserFilterData filter = filterSortRequest.getFilter();

        FilterSortRequest.SortRequest<UserSortBy> sort = filterSortRequest.getSort();

        Pagination pagination = filterSortRequest.getPagination();

        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getLimit());

        Workbook workbook = userService.getUserDetailsByResultSpi(filter, sort, pageRequest);

        assert workbook != null;

        ByteArrayResource resource = ExcelUtils.getBiteResourceFromWorkbook(workbook);

        return ResponseEntity.ok()

                .contentLength(resource.contentLength())

                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "userDetail_xlsx" + "\"")

                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))

                .body(resource);
    }


    @RequestMapping(name = "getUserEligibilityByAge", value = "/user/age", method = RequestMethod.POST)
    @Access(levels = Role.ANONYMOUS)

    public PageResponse<UserEligibilityAggregation> getUserEligibilityByAge(@RequestBody FilterSortRequest<UserFilterData, UserSortBy> filterSortRequest) throws JSONException {

        PageResponse<UserEligibilityAggregation> pageResponse = new PageResponse<>();

        pageResponse.setData(userService.getUserEligibilityByAge(filterSortRequest.getFilter(), filterSortRequest.getSort(),

                generalHelper.getPagination(filterSortRequest.getPagination().getPage(), filterSortRequest.getPagination().getLimit())));

        pageResponse.setStatus(Response.getOkResponse());
        return pageResponse;
    }

    @Access(levels = {Role.ADMIN})
    @RequestMapping(name = "userChartApi", value = "chart", method = RequestMethod.GET)

    public DataResponse<MonthAndYear> userChartApi(@RequestParam int year) {

        DataResponse<MonthAndYear> dataResponse = new DataResponse<>();

        dataResponse.setData(userService.userChartApi(year));

        dataResponse.setStatus(Response.getOkResponse());

        return dataResponse;
    }


}





