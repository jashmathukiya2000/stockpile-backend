package com.example.auth.service;

import com.example.auth.commons.JWTUser;
import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.decorator.ExcelUtils;
import com.example.auth.commons.enums.Role;
import com.example.auth.commons.helper.UserHelper;
import com.example.auth.commons.service.AdminConfigurationService;
import com.example.auth.commons.utils.JwtTokenUtil;
import com.example.auth.commons.utils.Utils;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.Pagination;
import com.example.auth.decorator.pagination.UserFilterData;
import com.example.auth.decorator.pagination.UserSortBy;
import com.example.auth.decorator.user.UserResponse;
import com.example.auth.decorator.user.UserSpiDataInExcel;
import com.example.auth.decorator.user.UserSpiResponse;
import com.example.auth.helper.UserServiceImplTestGenerator;
import com.example.auth.repository.UserRepository;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    private final static String id = "id";
    private final static String FullName = "sans km shukla";
    private final Role role = Role.ADMIN;
    private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJST0xFIjpbIlVTRVIiXSwic3ViIjoiNjM4NzM1Y2NhNjc5ODkwZmYxNTA3YTFmIiwiSUQiOiI2Mzg3MzVjY2E2Nzk4OTBmZjE1MDdhMWYiLCJleHAiOjE2NzQ2NDI5MDcsImlhdCI6MTY3NDU1NjUwN30.fZCV0ei64hZJGUZ1stmaOd_yb34PO21_aHh16kowM9U";
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final ModelMapper modelMapper = UserServiceImplTestGenerator.getModelMapper();
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean = Mockito.mock(NullAwareBeanUtilsBean.class);
    private final JwtTokenUtil jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
    private final UserHelper userHelper = mock(UserHelper.class);
    private final AdminConfigurationService adminConfigurationService = mock(AdminConfigurationService.class);
    private final Utils utils = mock(Utils.class);
    public UserServiceImpl userService = new UserServiceImpl(modelMapper, userRepository, nullAwareBeanUtilsBean, jwtTokenUtil, userHelper, adminConfigurationService, utils);


    @Test
    void testUpdateUser() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        //given
        var user = UserServiceImplTestGenerator.getMockUser(null);
        String FullName = userHelper.getFullName(user);
        var userAddRequest = UserServiceImplTestGenerator.getMockUserAddRequest();
        var userResponse = UserServiceImplTestGenerator.getMockUserResponse(null, null);
        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(user));
        //when

        userService.updateUser(id, userAddRequest);

        verify(userRepository, times(2)).findByIdAndSoftDeleteIsFalse(id);

    }

    @Test
    void testAddUser() throws InvocationTargetException, IllegalAccessException {
        //given
        String fullName = userHelper.getFullName();
        var user = UserServiceImplTestGenerator.getMockUser(null);

        var userAddRequest = UserServiceImplTestGenerator.getMockUserAddRequest();
        var userResponse = UserServiceImplTestGenerator.getMockUserResponse(null, fullName);
        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(user));
        when(userHelper.getFullName()).thenReturn(fullName);
        when(userRepository.save(user)).thenReturn(user);

        //when
        var actualData = userService.addUser(userAddRequest);

        //then
        Assertions.assertEquals(userResponse, actualData);

    }

    @Test
    void TestgetAllUser() {

        //given
        var user = UserServiceImplTestGenerator.getMockUsers();
        var response = UserServiceImplTestGenerator.getMockResponse();
        when(userRepository.findAllBySoftDeleteFalse()).thenReturn(user);

        //when
        var actualData = userService.getAllUser();


        //then
        Assertions.assertEquals(response, actualData);
    }


    @Test
    void TestgetUser() throws InvocationTargetException, IllegalAccessException {
        //given
        var user = UserServiceImplTestGenerator.getMockUser(null);
        var response = UserServiceImplTestGenerator.getMockUserResponse(null, FullName);
        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(user));

        //when
        var actualData = userService.getUser(id);

        //then
        Assertions.assertEquals(response, actualData);
    }


    @Test
    void deleteUser() {
        //given
        var user = UserServiceImplTestGenerator.getMockUser(null);
        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(user));

        //when
        userService.deleteUser(id);

        //then
        verify(userRepository, times(1)).findByIdAndSoftDeleteIsFalse(id);


    }

    @Test
    void TestUserByAge() {
        //given
        var userFilter = UserServiceImplTestGenerator.getMockUserFilter();
        var userResponse = UserServiceImplTestGenerator.getMockResponse();
        when(userRepository.getByFilterAndSoftDeleteFalse(userFilter)).thenReturn(userResponse);

        //when
        var actualData = userService.getUserByAge(userFilter);

        //then
        Assertions.assertEquals(userResponse, actualData);
    }

    @Test
    void TestUserBySalary() {
        //given
        var addUserFilter = UserServiceImplTestGenerator.getMockUserFilter();
        var response = UserServiceImplTestGenerator.getMockAggregationFilter();
        when(userRepository.getUserByAggregation(addUserFilter)).thenReturn(response);
        //when
        var actualData = userService.getUserBySalary(addUserFilter);

        //then
        Assertions.assertEquals(response, actualData);
    }


    @Test
    void testgetAllUserByPagination() {
        //given
        UserFilterData userFilter = new UserFilterData();
        userFilter.setId(userFilter.getId());
        FilterSortRequest.SortRequest<UserSortBy> sort = new FilterSortRequest.SortRequest<>();
        sort.setSortBy(UserSortBy.AGE);
        sort.setOrderBy(Sort.Direction.ASC);

        Pagination pagination = new Pagination();
        pagination.setPage(1);
        pagination.setLimit(5);
        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getLimit());

        var userResponse = UserServiceImplTestGenerator.getMockResponse();
        Page<UserResponse> page = new PageImpl<>(userResponse);
        when(userRepository.getAllUserByPagination(userFilter, sort, pageRequest)).thenReturn(page);

        //when
        var actualData = userService.getAllUserByPagination(userFilter, sort, pageRequest);

        //then
        Assertions.assertEquals(page, actualData);
    }

    @Test
    void testGetToken() throws InvocationTargetException, IllegalAccessException {
        //given
        var user = UserServiceImplTestGenerator.getMockUser(role);

        JWTUser jwtUser = new JWTUser(id, Collections.singletonList(user.getRole().toString()));

        String token = jwtTokenUtil.generateToken(jwtUser);

        var response = UserServiceImplTestGenerator.getResponse(role);

        response.setToken(token);

        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.ofNullable(user));

        //when
        var actualData = userService.getToken(id);

        //then
        Assertions.assertEquals(response, actualData);


    }

    @Test
    void testgetIdFromToken() {
        //given
        var user = UserServiceImplTestGenerator.getMockUser(role);

        JWTUser jwtUser = new JWTUser(id, Collections.singletonList(user.getRole().toString()));

        String id = jwtTokenUtil.getUserIdFromToken(token);

        when(userRepository.existsById(id)).thenReturn(true);

        //when
        var actualData = userService.getIdFromToken(token);

        //then
        Assertions.assertEquals(id, actualData);

    }

    @Test
    void testgetExpirationDateFromToken() {
        var user = UserServiceImplTestGenerator.getMockUser(role);

        JWTUser jwtUser = new JWTUser(id, Collections.singletonList(user.getRole().toString()));

        Date date = jwtTokenUtil.getExpirationDateFromToken(token);

        //when
        var actualData = userService.getExpirationDateFromToken(token);

        //then
        Assertions.assertEquals(date, actualData);

    }

    @Test
    void testisTokenExpired() {
        //given
        var user = UserServiceImplTestGenerator.getMockUser(role);

        JWTUser jwtUser = new JWTUser(id, Collections.singletonList(user.getRole().toString()));

        var date = jwtTokenUtil.isTokenExpired(token);

        when(jwtTokenUtil.isTokenExpired(token)).thenReturn(false);

        //when
        var actualData = userService.isTokenExpired(token);

        //then
        Assertions.assertEquals(date, actualData);

    }

    @Test
    void testGetAllUserInExcel() throws InvocationTargetException, IllegalAccessException {
        //given
        var response = UserServiceImplTestGenerator.getMockResponse();

        var userExcelResponse = UserServiceImplTestGenerator.getExcelResponse();

        var workbook = ExcelUtils.createWorkbookFromData(userExcelResponse, "UserDetails");

        //when
        var actualData = userService.getAllUserInExcel();

        //then
        Assertions.assertEquals(workbook.getNumberOfSheets(), actualData.getNumberOfSheets());
    }


    @Test
    void testGetUserDetailsByResultSpi() throws JSONException, InvocationTargetException, IllegalAccessException {
        //given

        UserFilterData userFilter = new UserFilterData();

        userFilter.setId(userFilter.getId());

        FilterSortRequest.SortRequest<UserSortBy> sort = new FilterSortRequest.SortRequest<>();

        sort.setSortBy(UserSortBy.SPI);

        sort.setOrderBy(Sort.Direction.ASC);

        Pagination pagination = new Pagination();

        pagination.setPage(0);

        pagination.setLimit(5);

        PageRequest pageRequest = PageRequest.of(pagination.getPage(), pagination.getLimit());

        var userSpiResponse = UserServiceImplTestGenerator.getSpiResponse();

        Page<UserSpiResponse> page = new PageImpl<>(userSpiResponse);

        HashMap<String, List<UserSpiDataInExcel>> userSpiDataInExcels = UserServiceImplTestGenerator.getSpiResponseInExcel();


        when(userRepository.getUserDetailsByResultSpi(userFilter, sort, pageRequest)).thenReturn(page);

        //when
        var actualData = userService.getUserDetailsByResultSpi(userFilter, sort, pageRequest);

        var expectedData = ExcelUtils.createWorkbookOnResultSpi(userSpiDataInExcels, "UserDetailsBySpi");

        //then
        Assertions.assertEquals(expectedData.getNumberOfSheets(), actualData.getNumberOfSheets());
    }


}





