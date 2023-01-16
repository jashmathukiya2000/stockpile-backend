package com.example.auth.service;

import com.example.auth.commons.enums.Role;
import com.example.auth.commons.utils.PasswordUtils;
import com.example.auth.helper.CustomerServiceTestGenerator;
import com.example.auth.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {
    private static final String email = "sanskritishukla4@gmail.com";
    private final CustomerRepository customerRepository = mock(CustomerRepository.class);
    private final ModelMapper modelMapper = CustomerServiceTestGenerator.getModelMapper();
//    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean = mock(NullAwareBeanUtilsBean.class);
    private final PasswordUtils passwordUtils = mock(PasswordUtils.class);

    public CustomerServiceImpl userModelService = spy(new CustomerServiceImpl(customerRepository, modelMapper, passwordUtils));

    @Test
    void TestSignupUser() {
        //given
        String password = userModelService.password("password");
        var userModel = CustomerServiceTestGenerator.getMockuserModel(password);
        var signUpAddRequest = CustomerServiceTestGenerator.getMockUserAddRequest();
        var signUpresponse = CustomerServiceTestGenerator.getMockSignUpResponse(password);
        when(PasswordUtils.encryptPassword("password")).thenReturn(password);
        when(customerRepository.save(userModel)).thenReturn(userModel);

        //when
        var actualData = userModelService.addCustomer(signUpAddRequest, Role.USER);

        //then
        Assertions.assertEquals(signUpresponse, actualData);

    }


    @Test
    void TestLogin() throws InvocationTargetException, IllegalAccessException, NoSuchAlgorithmException {
        //given
        String password = userModelService.password("password");
        var userModel = CustomerServiceTestGenerator.getMockuserModel(password);
        var loginAddRequst = CustomerServiceTestGenerator.getMockLoginRequest();
        var signUpResponse = CustomerServiceTestGenerator.getMockLoginResponse(password);
        when(customerRepository.findUserByEmailAndSoftDeleteIsFalse(email)).thenReturn(Optional.of(userModel));

        //when
        var actualData = userModelService.login(loginAddRequst);

        //then
        Assertions.assertEquals(signUpResponse, actualData);

    }


}
