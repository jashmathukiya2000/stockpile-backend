package com.example.auth.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.decorator.user.UserResponse;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.Pagination;
import com.example.auth.decorator.pagination.UserFilterData;
import com.example.auth.decorator.pagination.UserSortBy;
import com.example.auth.helper.UserServiceImplTestGenerator;
import com.example.auth.repository.UserRepository;
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
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    private final static String id = "id";
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final ModelMapper modelMapper = UserServiceImplTestGenerator.getModelMapper();
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean = Mockito.mock(NullAwareBeanUtilsBean.class);
    public UserService userService = new UserServiceImpl(modelMapper, userRepository, nullAwareBeanUtilsBean);


    @Test
    void testrUpdateUser() throws InvocationTargetException, IllegalAccessException {
        //given
        var userAddRequest = UserServiceImplTestGenerator.getMockUserAddRequest();
        var user = UserServiceImplTestGenerator.getMockUser();
        var userResponse = UserServiceImplTestGenerator.getMockUserResponse();
        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(user));
        //when

        var actualData = userService.addOrUpdateUser(id, userAddRequest);

        Assertions.assertEquals(userResponse, actualData);

    }

    @Test
    void testAddUser() throws InvocationTargetException, IllegalAccessException {
        //given

        var user = UserServiceImplTestGenerator.getMockUser();
        var userAddRequest = UserServiceImplTestGenerator.getMockUserAddRequest();
        var userResponse = UserServiceImplTestGenerator.getMockUserResponse();
        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        //when

        var actualData = userService.addOrUpdateUser(null, userAddRequest);

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
        var user = UserServiceImplTestGenerator.getMockUser();
        var response = UserServiceImplTestGenerator.getMockUserResponse();
        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(user));

        //when
        var actualData = userService.getUser(id);

        //then
        Assertions.assertEquals(response, actualData);
    }


    @Test
    void deleteUser() {
        //given
        var user = UserServiceImplTestGenerator.getMockUser();
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
    void testgetAllUserByPagination(){
        //given
        UserFilterData userFilter = new UserFilterData();
        userFilter.setId(userFilter.getId());
        FilterSortRequest.SortRequest<UserSortBy> sort=new FilterSortRequest.SortRequest<>();
        sort.setSortBy(UserSortBy.AGE);
        sort.setOrderBy(Sort.Direction.ASC);

        Pagination pagination= new Pagination();
        pagination.setPage(1);
        pagination.setLimit(5);
        PageRequest pageRequest=PageRequest.of(pagination.getPage(),pagination.getLimit());

        var userResponse=UserServiceImplTestGenerator.getMockResponse();
        Page<UserResponse> page=new PageImpl<>(userResponse);
        when(userRepository.getAllUserByPagination(userFilter,sort,pageRequest)).thenReturn(page);

        //when
      var actualData=  userService.getAllUserByPagination(userFilter,sort,pageRequest);

        //then
        Assertions.assertEquals(page,actualData);
    }
}
