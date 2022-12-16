package com.example.auth.service;

import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
import com.example.auth.decorator.UserResponse;
import com.example.auth.helper.UserServiceImplTestGenerator;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.google.common.base.Verify;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
@SpringBootTest
class UserServiceImplTest {
    private final static String id = "id";
    private final UserRepository userRepository= Mockito.mock(UserRepository.class);
    private final ModelMapper modelMapper= UserServiceImplTestGenerator.getModelMapper();
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean= Mockito.mock(NullAwareBeanUtilsBean.class);
    public UserService userService=new UserServiceImpl(modelMapper, userRepository, nullAwareBeanUtilsBean);


    @Test
    void TestgetUser()  {
        //given
        var userAddRequest=UserServiceImplTestGenerator.getMockUserAddRequest();
        var user=UserServiceImplTestGenerator.getMockUser();
        var userResponse=UserServiceImplTestGenerator.getMockUserResponse();
        Mockito.when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(user));
        //when
        UserResponse actualData= null;
        try {
            actualData = userService.addOrUpdateUser(id, userAddRequest);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        //then
        Assertions.assertEquals(userResponse,actualData);

    }
@Test
    void TestgetAllUser(){

        //given
        var user= UserServiceImplTestGenerator.getMockUsers();
        var response=UserServiceImplTestGenerator.getMockResponse();
          Mockito.when(userRepository.findAllBySoftDeleteFalse()).thenReturn(user);

        //when
       var actualData=userService.getAllUser();


        //then
         Assertions.assertEquals(response,actualData);
    }
}
