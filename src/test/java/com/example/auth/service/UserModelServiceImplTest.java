//package com.example.auth.service;
//
//import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
//import com.example.auth.common.config.enums.Role;
//import com.example.auth.decorator.SignUpAddRequest;
//import com.example.auth.decorator.SignUpResponse;
//import com.example.auth.decorator.UserAddRequest;
//import com.example.auth.helper.UserModelServiceTestGenerator;
//import com.example.auth.model.UserModel;
//import com.example.auth.repository.UserModelRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//class UserModelServiceImplTest {
//    private final UserModelRepository userModelRepository= Mockito.mock(UserModelRepository.class);
//    private final ModelMapper modelMapper= UserModelServiceTestGenerator.getModelMapper();
//    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean=Mockito.mock(NullAwareBeanUtilsBean.class);
//
// public    UserModelService service = new UserModelServiceImpl( userModelRepository,  modelMapper,  nullAwareBeanUtilsBean);
//
//    @Test
//    void TestsignUpUser() {
//        //given
//
//        var signAddRequest = UserModelServiceTestGenerator. getMockSignUpAddRequest();
//        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn()
//
//
//                var response = M. response();
//        //when
//        var actualResponse = service.signUpUser(signAddRequest, Role.USER);
//
//
//        //then
//        Assertions.assertAll(response, actualResponse);
//    }
//
//
//}