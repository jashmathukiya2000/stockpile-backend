package com.example.auth.service;

import com.example.auth.commons.enums.Role;
import com.example.auth.commons.utils.PasswordUtils;
import com.example.auth.helper.UserModelServiceTestGenerator;
import com.example.auth.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserModelServiceImplTest {
    private static final String email = "sans@1234gmail.com";
    private final CustomerRepository customerRepository = mock(CustomerRepository.class);
    private final ModelMapper modelMapper = UserModelServiceTestGenerator.getModelMapper();
//    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean = mock(NullAwareBeanUtilsBean.class);
    private final PasswordUtils passwordUtils = mock(PasswordUtils.class);

    public CustomerServiceImpl userModelService = spy(new CustomerServiceImpl(customerRepository, modelMapper, passwordUtils));

    @Test
    void TestSignupUser() {
        //given
        String password = userModelService.password("password");
        var userModel = UserModelServiceTestGenerator.getMockuserModel(password);
        var signUpAddRequest = UserModelServiceTestGenerator.getMockUserAddRequest();
        var signUpresponse = UserModelServiceTestGenerator.getMockSignUpResponse(password);
        when(PasswordUtils.encryptPassword("password")).thenReturn(password);
        when(customerRepository.save(userModel)).thenReturn(userModel);

        //when
        var actualData = userModelService.signUpUser(signUpAddRequest, Role.USER);

        //then
        Assertions.assertEquals(signUpresponse, actualData);

    }


    @Test
    void TestLogin() throws InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException {
        //given
        String password = userModelService.password("password");
        var userModel = UserModelServiceTestGenerator.getMockuserModel(password);
        var loginAddRequst = UserModelServiceTestGenerator.getMockLoginRequest();
        var signUpResponse = UserModelServiceTestGenerator.getMockLoginResponse();

        when(customerRepository.findUserByEmailAndSoftDeleteIsFalse(email)).thenReturn(Optional.of(userModel));

        //when
        var actualData = userModelService.login(loginAddRequst);

        //then
        Assertions.assertEquals(signUpResponse, actualData);

    }


}
