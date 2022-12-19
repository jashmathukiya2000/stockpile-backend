package com.example.auth.service;

import com.example.auth.common.config.advice.NullAwareBeanUtilsBean;
import com.example.auth.decorator.Result;
import com.example.auth.helper.ResultServiceImplTestGenerator;
import com.example.auth.helper.UserModelServiceTestGenerator;
import com.example.auth.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class ResultServiceImplTest {

    private static final String id = "id";

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean = Mockito.mock(NullAwareBeanUtilsBean.class);
    private final ModelMapper modelMapper = UserModelServiceTestGenerator.getModelMapper();

    public ResultService resultService = new ResultServiceImpl(userRepository, nullAwareBeanUtilsBean, modelMapper);

    @Test
    void TestAddResult() throws InvocationTargetException, IllegalAccessException {

        //given

        var user = ResultServiceImplTestGenerator.MockGetResult(null);

        var result = ResultServiceImplTestGenerator.MockAddResult();
        var resultResponse=ResultServiceImplTestGenerator.MockListResult();
        var response = ResultServiceImplTestGenerator.MockGetResult(resultResponse);

        when(userRepository.findByIdAndSoftDeleteIsFalse(id)).thenReturn(Optional.of(user));

//        Mockito.doNothing().when(nullAwareBeanUtilsBean).copyProperties(response,user);
        //when
        var actualData = resultService.addResult(id, result);

        //then
        Assertions.assertEquals(response, actualData);


    }

    @Test
    void TestgetBySpi() {

    }

}