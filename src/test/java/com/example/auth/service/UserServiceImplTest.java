package com.example.auth.service;

import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
import com.example.auth.helper.UserServiceImplTestGenerator;
import com.example.auth.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

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
    void testAddOrUpdateUser() throws InvocationTargetException, IllegalAccessException {
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
    void deleteUser(){
        var user = UserServiceImplTestGenerator.getMockUser();
        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(user));

        userService.deleteUser(id);
  verify(userRepository,times(1)).findByIdAndSoftDeleteIsFalse(eq(id));





    }

}
