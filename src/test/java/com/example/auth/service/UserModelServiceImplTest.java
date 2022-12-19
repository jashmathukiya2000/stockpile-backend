package com.example.auth.service;

import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
import com.example.auth.helper.UserModelServiceTestGenerator;
import com.example.auth.repository.UserModelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserModelServiceImplTest {
    private static final String email = "sans@1234gmail.com";
    private final UserModelRepository userModelRepository = mock(UserModelRepository.class);
    private final ModelMapper modelMapper = UserModelServiceTestGenerator.getModelMapper();
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean = mock(NullAwareBeanUtilsBean.class);
    public UserModelService userModelService = new UserModelServiceImpl(userModelRepository, modelMapper, nullAwareBeanUtilsBean);

    @Test
    void TestSignupUser() {

    }

    @Test
    void TestLogin() throws InvocationTargetException, IllegalAccessException {
        //given
        var userModel = UserModelServiceTestGenerator.getMockUserModel();
        var login = UserModelServiceTestGenerator.getMockLogin();
        var response = UserModelServiceTestGenerator.getMockSignUpResponse();
        when(userModelRepository.findUserByEmailAndSoftDeleteIsFalse(login.getEmail())).thenReturn(java.util.Optional.of(userModel));


        //when
        var actualData = userModelService.login(login);

        //then
        Assertions.assertEquals(response, actualData);

    }
}
