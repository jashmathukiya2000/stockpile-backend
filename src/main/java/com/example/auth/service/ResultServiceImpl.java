package com.example.auth.service;

import com.example.auth.commons.advice.NullAwareBeanUtilsBean;
import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.decorator.user.Result;
import com.example.auth.decorator.user.UserResponse;
import com.example.auth.decorator.user.UserSpiResponse;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j

public class ResultServiceImpl implements ResultService {

    private final UserRepository userRepository;
    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;
    private final ModelMapper modelMapper;

    public ResultServiceImpl(UserRepository userRepository, NullAwareBeanUtilsBean nullAwareBeanUtilsBean, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse addResult(String id, Result result) {
        List<Result> results = new ArrayList<>();
        List<Result> finalResults = new ArrayList<>();

        User user1 = getUserModel(id);

        double sum = 0;
        double cgpa = 0;
        if (!CollectionUtils.isEmpty(user1.getResult())) {
            results = user1.getResult();
            checkResultValidation(result);

            boolean matchFound = results.parallelStream()
                    .anyMatch(result1 -> result1.getSemester() == result.getSemester());

            if (matchFound) {
                throw new NotFoundException(MessageConstant.SEMESTER_ALREADY_EXIST);
            }

            finalResults.addAll(results);
        }
        finalResults.add(result);

        for (Result result1 : finalResults) {
            sum += result1.getSpi();
        }
        cgpa = sum / finalResults.size();
        cgpa = Double.parseDouble(new DecimalFormat("##.##").format(cgpa));
        user1.setCgpa(cgpa);
        user1.setResult(finalResults);
        userRepository.save(user1);
        UserResponse userResponse = new UserResponse();
        modelMapper.map(user1, userResponse);

        return userResponse;
    }


    @Override
    public List<UserSpiResponse> getBySpi(double spi) {
        return userRepository.getUserBySpi(spi);
    }


    public User getUserModel(String id) {
        return userRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.USER_ID_NOT_FOUND));
    }


    public void checkResultValidation(Result result) {
        if (result.getSemester() > 8) {
            throw new NotFoundException(MessageConstant.SEMESTER_NOT_VALID);
        }
        if (result.getSpi() > 10) {
            throw new NotFoundException(MessageConstant.SPI_NOT_VALID);
        }

    }


}
